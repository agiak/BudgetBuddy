package com.example.mywallet.features.home.domain

import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchAccounts(): Flow<List<AccountDB>>

    fun fetchLastTransactions(): Flow<List<TransactionDB>>

    fun fetchTotalBalance(): Flow<Double>

    suspend fun deleteAccount(accountID: Long)
}