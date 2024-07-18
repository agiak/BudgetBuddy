package com.example.mywallet.features.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.disableFullScreenTheme
import com.example.common.myutils.setLightStatusBars
import com.example.core.presentation.ext.addSpaceDecorator
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.databinding.FragmentHomeBinding
import com.example.mywallet.databinding.ItemHomeAccountsBinding
import com.example.mywallet.databinding.ItemHomeActivityBinding
import com.example.mywallet.databinding.ItemHomeAddAccountBinding
import com.example.mywallet.databinding.ItemHomeSummaryBinding
import com.example.mywallet.databinding.ItemHomeTransferFundsBinding
import com.example.mywallet.features.home.data.HomeItem
import com.example.mywallet.features.home.presentation.accounts.AccountsViewHolder
import com.example.mywallet.features.home.presentation.accounts.AddAccountViewHolder
import com.example.mywallet.features.home.presentation.activity.ActivityViewHolder
import com.example.mywallet.features.home.presentation.summary.SummaryViewHolder
import com.example.mywallet.features.home.presentation.transfer.TransferFundsViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
            viewModel.homeState.collectLatest { state ->
                homeAdapter.submitList(state)
            }
        }
    }

    private fun initViews() {
        homeAdapter = HomeAdapter(createViewHolders())
        binding.homeList.apply {
            adapter = homeAdapter
            addSpaceDecorator(24)
        }
    }

    private fun createViewHolders(): List<HomeViewHolderFactory<*>> {
        val summaryViewHolder = HomeViewHolderFactory<HomeItem.Summary> { parent ->
            val binding = ItemHomeSummaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SummaryViewHolder(binding = binding, addMoneyClick = {})
        }

        val activityViewHolder = HomeViewHolderFactory<HomeItem.Activity> { parent ->
            val binding = ItemHomeActivityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ActivityViewHolder(
                binding = binding,
                navigateToTransaction = { navigateToTransactionDetails(it) },
            )
        }

        val accountsViewHolder = HomeViewHolderFactory<HomeItem.Accounts> { parent ->
            val binding = ItemHomeAccountsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AccountsViewHolder(binding = binding,
                navigateToAllAccounts = { navigateToAllAccounts() },
                navigateToAccount = { navigateToAccountDetails(it) }
            )
        }

        val transferFundsViewHolder = HomeViewHolderFactory<HomeItem.TransferFunds> { parent ->
            val binding = ItemHomeTransferFundsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            TransferFundsViewHolder(binding = binding,
                onTransferFundsClick = { navigateToAddTransaction() },
                onAddRulesClick = { navigateToRules() }
            )
        }

        val addAccountViewHolder = HomeViewHolderFactory<HomeItem.AddAccount> { parent ->
            val binding = ItemHomeAddAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AddAccountViewHolder(
                binding = binding,
                navigateToAddAccount = { navigateToAddAccount() },
            )
        }

        return listOf(
            summaryViewHolder,
            transferFundsViewHolder,
            activityViewHolder,
            accountsViewHolder,
            addAccountViewHolder,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun HomeFragment.navigateToAddTransaction() =
    navigateToNextScreen(HomeFragmentDirections.actionNavigationHomeToNavigationTransactionAdd())

private fun HomeFragment.navigateToAllAccounts() =
    navigateToNextScreen(HomeFragmentDirections.actionNavigationHomeToNavigationAccounts())


private fun HomeFragment.navigateToRules() =
    navigateToNextScreen(HomeFragmentDirections.actionNavigationHomeToGraphRules())

private fun HomeFragment.navigateToTransactionDetails(transactionID: Long) =
    navigateToNextScreen(
        HomeFragmentDirections.actionNavigationHomeToNavigationTransaction(
            transactionID
        )
    )

private fun HomeFragment.navigateToAccountDetails(accountID: Long) =
   navigateToNextScreen(HomeFragmentDirections.actionNavigationHomeToNavigationAccount(accountID))

private fun HomeFragment.navigateToAddAccount() =
    navigateToNextScreen(HomeFragmentDirections.actionNavigationHomeToNavigationAddAccount())
