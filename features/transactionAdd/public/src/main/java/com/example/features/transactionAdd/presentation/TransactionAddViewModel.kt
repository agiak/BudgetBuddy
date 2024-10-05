package com.example.features.transactionAdd.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.error.UiText
import com.agcoding.features.transactionAdd.impl.R
import com.example.features.transactionAdd.impl.data.AccountSelection
import com.example.features.transactionAdd.impl.data.AddTransactionUiState
import com.example.features.transactionAdd.impl.data.InsertTransactionState
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
) : ViewModel() {

    private val _state = MutableStateFlow<AddTransactionUiState>(AddTransactionUiState.Idle)
    val state: StateFlow<AddTransactionUiState> = _state.asStateFlow()

    val accounts: StateFlow<List<AccountSelection>> = repository.getAccounts()
        .map { storedAccounts -> storedAccounts.toAccountSelections() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun createAccount(transaction: TransactionNew) {
        viewModelScope.launch {
            _state.value = AddTransactionUiState.Loading
            runCatching {
                repository.addTransaction(
                    transactionDB = transaction.toStoreTransaction(),
                    applyTransaction = transaction.applyTransaction
                )
            }.onSuccess { result ->
                when (result) {
                    InsertTransactionState.AccountNotFound -> _state.value =
                        AddTransactionUiState.Error(
                            UiText.StringResource(
                                R.string.error_account_not_found
                            )
                        )

                    InsertTransactionState.InsufficientFunds -> _state.value =
                        AddTransactionUiState.Error(
                            UiText.StringResource(
                                R.string.error_insufficient_amount
                            )
                        )

                    InsertTransactionState.Success -> _state.value = AddTransactionUiState.Success
                }
            }.onFailure {
                Timber.e(it)
                _state.value = AddTransactionUiState.Error(UiText.Dynamic(it.message.toString()))
            }
        }
    }
}
