package com.example.features.transactionAdd.impl.domain

import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.features.transactionAdd.impl.data.InsertTransactionState
import kotlinx.coroutines.flow.Flow

interface TransactionAddRepository {

    suspend fun addTransaction(transactionDB: TransactionDB, applyTransaction: Boolean): InsertTransactionState

    fun getAccounts(): Flow<List<AccountDB>>
}