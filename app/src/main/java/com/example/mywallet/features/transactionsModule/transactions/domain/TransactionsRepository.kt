package com.example.mywallet.features.transactionsModule.transactions.domain

import com.example.mywallet.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun fetchTransactions(): Flow<List<TransactionDB>>

    suspend fun deleteTransaction(transactionID: Long)
}