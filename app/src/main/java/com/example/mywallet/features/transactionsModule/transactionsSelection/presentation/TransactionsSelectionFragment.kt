package com.example.mywallet.features.transactionsModule.transactionsSelection.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.hide
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showSnackBar
import com.example.common.myutils.showToast
import com.example.mywallet.R
import com.example.mywallet.core.presentation.ext.launchWhenResumed
import com.example.mywallet.databinding.FragmentTransactionsSelectionBinding
import com.example.mywallet.features.transactionsModule.fileTransactions.data.FileGuidanceState
import com.example.mywallet.features.transactionsModule.fileTransactions.presentation.FileTransactionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TransactionsSelectionFragment : Fragment() {

    private var _binding: FragmentTransactionsSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FileTransactionsViewModel by hiltNavGraphViewModels(R.id.graph_transactions_via_file)

    private val transactionsAdapter: TransactionsSelectionAdapter by lazy {
        TransactionsSelectionAdapter(onClick = { viewModel.onTransactionSelected(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionsSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                binding.loader.hide()
                when (state) {
                    is FileGuidanceState.Error -> showToast(state.errorMessage)
                    FileGuidanceState.Idle -> {}
                    FileGuidanceState.Loading -> binding.loader.show()
                    is FileGuidanceState.Result -> {
                        transactionsAdapter.submitList(state.transactions)
                    }
                    is FileGuidanceState.TransactionsSaved -> {
                        showSnackBar("Added ${state.transactionsAddedSize} transactions")
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.actionBtn.setOnClickListener { viewModel.saveTransactions() }
        binding.transactionsList.adapter = transactionsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}