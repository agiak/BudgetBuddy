package com.example.mywallet.features.transactionsModule.transactions.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.myutils.setLightStatusBars
import com.example.mywallet.core.presentation.ext.addSpaceDecorator
import com.example.mywallet.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.databinding.FragmentTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        binding.btnAddTransaction.setOnClickListener { navigateToAddTransaction() }
        binding.transactionsList.apply {
            adapter = transactionsAdapter
            addSpaceDecorator(36)
        }
    }

    private fun initSubscriptions() {
        lifecycleScope.launch {
            viewModel.transactions.collectLatest {
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
    navigateToNextScreen(TransactionsFragmentDirections.actionNavigationTransactionsToNavigationTransactionAdd())

private fun TransactionsFragment.navigateToTransactionDetails(transactionID: Long) =
    navigateToNextScreen(
        TransactionsFragmentDirections.actionNavigationTransactionsToNavigationTransaction(
            transactionID
        )
    )
