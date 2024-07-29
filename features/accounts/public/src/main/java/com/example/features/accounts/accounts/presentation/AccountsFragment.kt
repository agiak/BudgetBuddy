package com.example.features.accounts.accounts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.addTitleElevationAnimation
import com.example.common.myutils.disableFullScreenTheme
import com.example.common.myutils.scrollToUp
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.core.presentation.ext.onBack
import com.example.features.accounts.R
import com.example.features.accounts.databinding.FragmentAccountsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private val accountsAdapter = AccountsAdapter(onClick = { navigateToAccountDetail(it) })

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
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.screenTitle.text = getString(com.example.features.accounts.impl.R.string.accounts_screen_title)
        binding.toolbar.backButton.setOnClickListener { onBack() }
        binding.toolbar.optionsButton.apply {
            setImageResource(R.drawable.ic_filter)
            setOnClickListener { navigateToFilters() }
            show()
        }
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.accounts.collectLatest { accounts ->
                accountsAdapter.submitList(accounts)
                binding.accountsList.scrollToUp()
            }
        }
    }

    private fun initViews() {
        binding.btnAddAccount.setOnClickListener { navigateToAddAccount() }
        binding.accountsList.apply {
            adapter = accountsAdapter
            addTitleElevationAnimation(binding.toolbar.root)
        }
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