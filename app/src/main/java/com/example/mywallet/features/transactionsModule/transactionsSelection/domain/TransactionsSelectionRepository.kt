package com.example.mywallet.features.transactionsModule.transactionsSelection.domain

import com.example.core.storage.data.TransactionDB

interface TransactionsSelectionRepository {

    suspend fun saveTransactions(transactions: MutableMap<TransactionDB, Boolean>): Int
}