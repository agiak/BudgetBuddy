package com.example.features.transactionAdd.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.features.transactionAdd.impl.data.AccountSelection
import com.example.features.transactionAdd.impl.data.TransactionNew
import com.example.features.transactionAdd.impl.data.toAccountSelections
import com.example.features.transactionAdd.impl.data.toStoreTransaction
import com.example.features.transactionAdd.impl.domain.TransactionAddRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransactionAddViewModel @Inject constructor(
    private val repository: TransactionAddRepository,
): ViewModel() {

    private val _state = MutableStateFlow<AddTransactionUiState>(AddTransactionUiState.Idle)
    val state: StateFlow<AddTransactionUiState> = _state.asStateFlow()

    val accounts: StateFlow<List<AccountSelection>> = repository.getAccounts()
        .map { storedAccounts -> storedAccounts.toAccountSelections() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun createAccount(transaction: TransactionNew) {
        viewModelScope.launch {
            _state.value = AddTransactionUiState.Loading
            runCatching {
                repository.addTransaction(transactionDB = transaction.toStoreTransaction(), transaction.applyTransaction)
            }.onSuccess {
                _state.value = AddTransactionUiState.Success
            }.onFailure {
                Timber.e(it)
                _state.value = AddTransactionUiState.Error(it.message.toString())
            }
        }
    }

}

sealed class AddTransactionUiState {
    data object Idle: AddTransactionUiState()
    data object Loading: AddTransactionUiState()
    data object Success: AddTransactionUiState()
    data class Error(val message: String): AddTransactionUiState()
}