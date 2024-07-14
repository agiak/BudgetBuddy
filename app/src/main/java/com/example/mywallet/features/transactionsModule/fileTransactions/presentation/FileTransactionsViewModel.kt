package com.example.mywallet.features.transactionsModule.fileTransactions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.features.transactionsModule.fileTransactions.data.FileGuidanceState
import com.example.mywallet.features.transactionsModule.fileTransactions.domain.FileGuideRepository
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.TransactionSelection
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.isEqual
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.toTransactionSelectionList
import com.example.mywallet.features.transactionsModule.transactionsSelection.domain.TransactionsSelectionRepository
import com.example.mywallet.storage.data.TransactionDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileTransactionsViewModel @Inject constructor(
    private val repository: FileGuideRepository,
    private val transactionSelectionRepository: TransactionsSelectionRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<FileGuidanceState> =
        MutableStateFlow(FileGuidanceState.Idle)
    val state: StateFlow<FileGuidanceState> = _state.asStateFlow()

    private val mapTransactions: MutableMap<TransactionDB, Boolean> = mutableMapOf()

    fun parseFile(file: File) {
        viewModelScope.launch {
            _state.value = FileGuidanceState.Loading
            runCatching {
                repository.createTransactions(file)
            }.onSuccess {
                mapTransactions.putAll(it.associateWith { true })
                _state.value = FileGuidanceState.Result(it.toTransactionSelectionList())
            }.onFailure {
                Timber.e(it)
                _state.value = FileGuidanceState.Error(it.message.toString())
            }
        }
    }

    fun onTransactionSelected(transactionSelection: TransactionSelection) {
        val transactionDB = mapTransactions.keys.find { it.isEqual(transactionSelection) }!!
        mapTransactions[transactionDB] = transactionSelection.isSelected
    }

    fun saveTransactions() {
        viewModelScope.launch {
            runCatching {
                transactionSelectionRepository.saveTransactions(mapTransactions)
            }.onSuccess {
                _state.value = FileGuidanceState.TransactionsSaved(it)
            }.onFailure {

            }
        }
    }
}