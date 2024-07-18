package com.example.mywallet.features.statics.domain

import com.example.mywallet.core.data.bank.Bank
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB

interface StaticsRepository {

    suspend fun init()

    suspend fun fetchTransactions(): List<TransactionDB>

    suspend fun fetchAccounts(): List<AccountDB>

    suspend fun fetchMostValuableAccounts(): List<AccountDB>

    suspend fun fetchMostLargerTransactions(): List<TransactionDB>

    suspend fun fetchMostUsedAccounts(): Map<AccountDB, Int>

    suspend fun fetchMostTrustedBanks(): Map<Bank, Double>
}