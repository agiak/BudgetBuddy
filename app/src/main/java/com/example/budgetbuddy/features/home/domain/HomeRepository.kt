package com.example.budgetbuddy.features.home.domain

import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchAccounts(): Flow<List<AccountDB>>

    fun fetchLastTransactions(): Flow<List<TransactionDB>>

    fun fetchTotalBalance(): Flow<Double>

    suspend fun deleteAccount(accountID: Long)
}