package com.example.mywallet.features.accountModule.accounts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.myutils.disableFullScreenTheme
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.showToast
import com.example.mywallet.R
import com.example.mywallet.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.core.presentation.options.OptionsScreen
import com.example.mywallet.databinding.FragmentAccountsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountsFragment : Fragment(), OptionsScreen {

    private val viewModel: AccountsViewModel by viewModels()

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private val adapter = AccountsAdapter(onClick = { navigateToAccountDetail(it) })

    override val optionButtonDrawable: Int
        get() = R.drawable.ic_filter

    override fun onOptionButtonClick() {
        showToast("Filter was called")
    }

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
        lifecycleScope.launch {
            viewModel.accounts.collectLatest { accounts ->
                adapter.submitList(accounts)
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
