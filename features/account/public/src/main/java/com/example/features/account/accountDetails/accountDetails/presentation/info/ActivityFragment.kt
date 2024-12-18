package com.example.features.account.accountDetails.accountDetails.presentation.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.core.presentation.ext.addSpaceDecorator
import com.example.core.presentation.ext.launchWhenResumed
import com.agcoding.features.account.R
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountDetailsItem
import com.agcoding.features.account.databinding.FragmentAccountActivityBinding
import com.agcoding.features.account.databinding.ItemAccountDetailsActivityBinding
import com.agcoding.features.account.databinding.ItemAccountDetailsStaticsBinding
import com.example.features.account.accountDetails.accountDetails.presentation.AccountDetailsAdapter
import com.example.features.account.accountDetails.accountDetails.presentation.AccountUiState
import com.example.features.account.accountDetails.accountDetails.presentation.AccountViewModel
import com.example.features.account.accountDetails.accountDetails.presentation.viewholders.AccountDetailsViewHolderFactory
import com.example.features.account.accountDetails.accountDetails.presentation.viewholders.activity.AccountActivityViewHolder
import com.example.features.account.accountDetails.accountDetails.presentation.viewholders.statics.AccountStaticsViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ActivityFragment : Fragment() {

    private var _binding: FragmentAccountActivityBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by hiltNavGraphViewModels(R.id.graph_account)

    private lateinit var accountAdapter: AccountDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                when (state) {
                    AccountUiState.Loading -> {}
                    is AccountUiState.Result -> {
                        accountAdapter.submitList(state.accountItems)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initViews() {
        accountAdapter = AccountDetailsAdapter(createViewHolders())
        binding.accountDetailsList.apply {
            adapter = accountAdapter
            addSpaceDecorator(32)
        }
    }

    private fun createViewHolders(): List<AccountDetailsViewHolderFactory<*>> =
        listOf(
            AccountDetailsViewHolderFactory<AccountDetailsItem.Statics> { parent ->
                val binding = ItemAccountDetailsStaticsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AccountStaticsViewHolder(binding = binding)
            },
            AccountDetailsViewHolderFactory<AccountDetailsItem.Activity> { parent ->
                val binding = ItemAccountDetailsActivityBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AccountActivityViewHolder(binding = binding)
            },
        )
}