package com.example.features.transactions.transactions.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.setLightStatusBars
import com.example.core.presentation.ext.addSpaceDecorator
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.features.transactions.databinding.FragmentTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private val viewModel: TransactionsViewModel by viewModels()

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private val transactionsAdapter = TransactionsAdapter(
        onClick = { navigateToTransactionDetails(it) },
        onDelete = { viewModel.deleteTransaction(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initViews() {
        setUpFloatingButtons()

        binding.transactionsList.apply {
            adapter = transactionsAdapter
            addSpaceDecorator(12)
        }

        binding.btnDelete.setOnClickListener { viewModel.deleteTransactions() }
    }

    private fun setUpFloatingButtons() {
        binding.btnAddTransaction.apply {
            shrink()
            setOnClickListener {
                when {
                    !binding.fabGroup.isVisible -> {
                        binding.fabGroup.isVisible = true
                        binding.btnAddTransaction.extend()
                    }

                    else -> {
                        binding.fabGroup.isVisible = false
                        binding.btnAddTransaction.shrink()
                    }
                }
            }
        }

        binding.btnAddTransactionManually.setOnClickListener { navigateToAddTransaction() }
        binding.btnAddTransactionViaFile.setOnClickListener { navigateToAddTransactionsViaFile() }

    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.transactions.collectLatest {
                binding.emptyState.isVisible = it.isEmpty()
                transactionsAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun TransactionsFragment.navigateToAddTransaction() =
    navigateToNextScreen(TransactionsFragmentDirections.actionNavigationTransactionsToNavigationAddTransaction())


private fun TransactionsFragment.navigateToTransactionDetails(transactionID: Long) =
    navigateToNextScreen(
        TransactionsFragmentDirections.actionNavigationTransactionsToNavigationTransaction(
            transactionID
        )
    )

private fun TransactionsFragment.navigateToAddTransactionsViaFile() =
    navigateToNextScreen(TransactionsFragmentDirections.actionNavigationTransactionsToGraphTransactionsViaFile())
