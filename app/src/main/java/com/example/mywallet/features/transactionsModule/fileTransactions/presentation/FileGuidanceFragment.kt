package com.example.mywallet.features.transactionsModule.fileTransactions.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.common.myutils.hide
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showToast
import com.example.mywallet.R
import com.example.mywallet.core.presentation.ext.launchWhenResumed
import com.example.mywallet.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.databinding.FragmentTransactionsViaFileBinding
import com.example.mywallet.features.transactionsModule.fileTransactions.data.FileGuidanceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.io.File

private operator fun <T> List<T>.component6(): T = get(5)

@AndroidEntryPoint
class FileGuidanceFragment : Fragment() {

    private var _binding: FragmentTransactionsViaFileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FileTransactionsViewModel by hiltNavGraphViewModels(R.id.graph_transactions_via_file)

    private val filePickerLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    Timber.d("file uri $uri")
                    val file = File.createTempFile("tempTransactions", ".csv")
                    requireContext().contentResolver.openInputStream(uri).use { input ->
                        file.outputStream().use { output ->
                            input?.copyTo(output)
                        }
                        viewModel.parseFile(file)
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionsViaFileBinding.inflate(inflater, container, false)
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
                    FileGuidanceState.Loading -> binding.loader.show()
                    is FileGuidanceState.Result -> {
                        Timber.d("fetched list ${state.transactions}")
                        navigateToTransactionSelection()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initViews() {
        binding.actionBtn.setOnClickListener { openFilePicker() }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("*/*")
            )
        }
        filePickerLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun FileGuidanceFragment.navigateToTransactionSelection() =
    navigateToNextScreen(FileGuidanceFragmentDirections.actionNavigationTransactionsViaFileToNavigationSelectTransactions())
