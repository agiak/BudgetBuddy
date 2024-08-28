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
                .sortedWith { str1, str2 ->
                    val (day1, month1, year1) = str1.date.split("/").map { it.toIntOrNull() ?: 0 }
                    val (day2, month2, year2) = str2.date.split("/").map { it.toIntOrNull() ?: 0 }

                    // Compare years first, then months, then days
                    if (year1 != year2) {
                        year1.compareTo(year2)
                    } else if (month1 != month2) {
                        month1.compareTo(month2)
                    } else {
                        day1.compareTo(day2)
                    }
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun deleteTransaction(accountID: Long) {
        viewModelScope.launch {
            runCatching {
                repository.deleteTransaction(accountID)
            }
        }
    }

    fun deleteTransactions() {
        viewModelScope.launch {
            runCatching {
                repository.deleteTransactions()
            }
        }
    }
}