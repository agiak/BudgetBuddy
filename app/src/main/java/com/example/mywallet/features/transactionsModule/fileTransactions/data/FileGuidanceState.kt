package com.example.mywallet.features.transactionsModule.fileTransactions.data

import com.example.mywallet.features.transactionsModule.transactionsSelection.data.TransactionSelection

sealed class FileState {
    data object Success : FileState()
    data object Idle : FileState()
    data class Saved(val transactionsAdded: Int) : FileState()
}