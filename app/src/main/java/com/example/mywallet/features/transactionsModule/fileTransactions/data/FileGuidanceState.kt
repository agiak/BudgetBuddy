package com.example.mywallet.features.transactionsModule.fileTransactions.data

import com.example.mywallet.features.transactionsModule.transactionsSelection.data.TransactionSelection

sealed class FileGuidanceState {
    data object Idle: FileGuidanceState()
    data object Loading: FileGuidanceState()
    data class Error(val errorMessage: String): FileGuidanceState()
    data class Result(val transactions: List<TransactionSelection>): FileGuidanceState()
    data class TransactionsSaved(val transactionsAddedSize: Int): FileGuidanceState()
}