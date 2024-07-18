package com.example.mywallet.features.statics.domain

import com.example.core.data.bank.Bank
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB

interface StaticsRepository {

    suspend fun init()

    suspend fun fetchTransactions(): List<TransactionDB>

    suspend fun fetchAccounts(): List<AccountDB>

    suspend fun fetchMostValuableAccounts(): List<AccountDB>

    suspend fun fetchMostLargerTransactions(): List<TransactionDB>

    suspend fun fetchMostUsedAccounts(): Map<AccountDB, Int>

    suspend fun fetchMostTrustedBanks(): Map<Bank, Double>
}