package com.example.mywallet.features.transactionsModule.fileTransactions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.features.transactionsModule.fileTransactions.data.FileState
import com.example.mywallet.features.transactionsModule.fileTransactions.domain.FileGuideRepository
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.TransactionSelection
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.isEqual
import com.example.mywallet.features.transactionsModule.transactionsSelection.data.toTransactionSelectionList
import com.example.mywallet.features.transactionsModule.transactionsSelection.domain.TransactionsSelectionRepository
import com.example.core.storage.data.TransactionDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileTransactionsViewModel @Inject constructor(
    private val repository: FileGuideRepository,
    private val transactionSelectionRepository: TransactionsSelectionRepository,
) : ViewModel() {

    private val _transactions: MutableStateFlow<List<TransactionSelection>> =
        MutableStateFlow(emptyList())
    val transactions: StateFlow<List<TransactionSelection>> = _transactions.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _state: MutableStateFlow<FileState> =
        MutableStateFlow(FileState.Idle)
    val state: StateFlow<FileState> = _state.asStateFlow()

    private val mapTransactions: MutableMap<TransactionDB, Boolean> = mutableMapOf()

    fun parseFile(file: File) {
        viewModelScope.launch {
            _loading.update { true }
            runCatching {
                repository.createTransactions(file)
            }.onSuccess { retrievedTransactions ->
                mapTransactions.putAll(retrievedTransactions.associateWith { true })
                _transactions.update { retrievedTransactions.toTransactionSelectionList() }
                _loading.update { false }
                _state.update { FileState.Success }
            }.onFailure { error ->
                Timber.e(error)
                _loading.update { false }
                _error.update { error.message.toString() }
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
            }.onSuccess { transactionsAdded ->
                _state.update { FileState.Saved(transactionsAdded) }
            }.onFailure {

            }
        }
    }

    fun clearState() {
        _state.update { FileState.Idle }
    }
}