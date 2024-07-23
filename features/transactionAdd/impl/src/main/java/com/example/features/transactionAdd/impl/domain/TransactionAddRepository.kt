package com.example.features.transactionAdd.impl.domain

import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

interface TransactionAddRepository {

    suspend fun addTransaction(transactionDB: TransactionDB, applyTransaction: Boolean)

    fun getAccounts(): Flow<List<AccountDB>>
}