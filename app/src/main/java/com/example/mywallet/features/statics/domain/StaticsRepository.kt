package com.example.mywallet.features.statics.domain

import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB

interface StaticsRepository {

    suspend fun init()

    suspend fun fetchTransactions(): List<TransactionDB>

    suspend fun fetchAccounts(): List<AccountDB>
}