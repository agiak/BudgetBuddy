package com.example.mywallet.features.transactionsModule.transactionsSelection.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.addTitleElevationAnimation
import com.example.common.myutils.hide
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.showSnackBar
import com.example.common.myutils.showToast
import com.example.mywallet.R
import com.example.core.presentation.ext.launchWhenResumed
import com.example.mywallet.databinding.FragmentTransactionsSelectionBinding
import com.example.mywallet.features.transactionsModule.fileTransactions.data.FileState
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
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.screenTitle.text =
            getString(R.string.file_import_transactions_selection_screen_title)
        binding.toolbar.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.toolbar.optionsButton.hide()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.loading.collectLatest { isLoading ->
                binding.loader.isVisible = isLoading
            }
        }
        launchWhenResumed { viewModel.error.collectLatest { error -> error?.let { showToast(it) } } }

        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is FileState.Saved -> {
                        showSnackBar("Added ${state.transactionsAdded} transactions")
                        findNavController().navigateUp()
                    }

                    else -> {}
                }
            }
        }

        launchWhenResumed {
            viewModel.transactions.collectLatest {
                transactionsAdapter.submitList(it)
            }
        }
    }

    private fun initViews() {
        binding.actionBtn.setOnClickListener { viewModel.saveTransactions() }
        binding.transactionsList.apply {
            adapter = transactionsAdapter
            addTitleElevationAnimation(binding.toolbar.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}