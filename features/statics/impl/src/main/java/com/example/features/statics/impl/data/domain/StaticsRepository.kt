package com.example.features.statics.impl.data.domain

import com.example.core.data.bank.Bank
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.features.statics.impl.data.data.StatsData
import kotlinx.coroutines.flow.Flow

interface StaticsRepository {

    fun getStatsObservable(): Flow<StatsData>

    suspend fun init()

    suspend fun fetchTransactions(): List<TransactionDB>

    suspend fun fetchAccounts(): List<AccountDB>

    suspend fun fetchMostValuableAccounts(): List<AccountDB>

    suspend fun fetchMostLargerTransactions(): List<TransactionDB>

    suspend fun fetchMostUsedAccounts(): Map<AccountDB, Int>

    suspend fun fetchMostTrustedBanks(): Map<Bank, Double>

    suspend fun fetchInvestmentProgress(): Map<String, Double>
}