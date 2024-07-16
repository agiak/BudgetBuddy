package com.example.mywallet.features.account.accounts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.disableFullScreenTheme
import com.example.common.myutils.scrollToUp
import com.example.common.myutils.setLightStatusBars
import com.example.mywallet.R
import com.example.mywallet.core.presentation.ext.launchWhenResumed
import com.example.mywallet.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.core.presentation.options.OptionsScreen
import com.example.mywallet.databinding.FragmentAccountsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AccountsFragment : Fragment(), OptionsScreen {

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private val adapter = AccountsAdapter(onClick = { navigateToAccountDetail(it) })

    override val optionButtonDrawable: Int
        get() = R.drawable.ic_filter

    override fun onOptionButtonClick() { navigateToFilters() }

    private val viewModel: AccountsViewModel by hiltNavGraphViewModels(R.id.graph_accounts)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableFullScreenTheme()
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.accounts.collectLatest { accounts ->
                adapter.submitList(accounts)
                binding.accountsList.scrollToUp()
            }
        }
    }

    private fun initViews() {
        binding.btnAddAccount.setOnClickListener { navigateToAddAccount() }
        binding.accountsList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

private fun AccountsFragment.navigateToAccountDetail(accountID: Long) =
    navigateToNextScreen(
        AccountsFragmentDirections.actionNavigationAccountsToNavigationAccount(
            accountID
        )
    )

private fun AccountsFragment.navigateToAddAccount() =
    navigateToNextScreen(AccountsFragmentDirections.actionNavigationAccountsToNavigationAddAccount())

private fun AccountsFragment.navigateToFilters() =
    findNavController().navigate(AccountsFragmentDirections.actionNavigationAccountsToNavigationAccountsFilter())