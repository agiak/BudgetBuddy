package com.example.features.transactions.impl.transactions.domain

import com.example.core.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun fetchTransactions(): Flow<List<TransactionDB>>

    suspend fun deleteTransaction(transactionID: Long)

    suspend fun deleteTransactions()
}