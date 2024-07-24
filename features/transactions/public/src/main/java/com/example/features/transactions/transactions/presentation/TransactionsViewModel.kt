package com.example.features.transactions.transactions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.features.transactions.impl.transactions.data.Transaction
import com.example.features.transactions.impl.transactions.data.toTransactions
import com.example.features.transactions.impl.transactions.domain.TransactionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: TransactionsRepository,
) : ViewModel() {

    val transactions: StateFlow<List<Transaction>> = repository.fetchTransactions()
        .map { storedTransactions ->
            storedTransactions
                .toTransactions()
                .sortedByDescending { it.id }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun deleteTransaction(accountID: Long) {
        viewModelScope.launch {
            runCatching {
                repository.deleteTransaction(accountID)
            }
        }
    }
}