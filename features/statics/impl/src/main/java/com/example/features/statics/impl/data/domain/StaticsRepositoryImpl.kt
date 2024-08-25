package com.example.features.statics.impl.data.domain

import com.example.common.myutils.formatToDateString
import com.example.core.data.bank.Bank
import com.example.core.data.common.TransactionType
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.data.data.StatsData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StaticsRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val transactionsDao: TransactionDao,
    private val accountsDao: AccountDao,
) : StaticsRepository {

    private var transactions: List<TransactionDB> = emptyList()
    private var accounts: List<AccountDB> = emptyList()

    override fun getStatsObservable(): Flow<StatsData> =
        accountsDao.getAllAccountsObservable()
            .combine(transactionsDao.getAllTransactionsObservable()) { accountsRetrieved, transactionsRetrieved ->
                transactions = transactionsRetrieved
                accounts = accountsRetrieved
                StatsData(accounts, transactions)
            }

    suspend fun getStatsData(): List<StaticsItem> =
        withContext(dispatchers.backgroundThread()) {


            emptyList()
        }


    override suspend fun init() {
        withContext(dispatchers.backgroundThread()) {
            runCatching {
                val transactionsDeferred = async { transactionsDao.getAllTransactions() }
                val accountsDeferred = async { accountsDao.getAllAccounts() }

                val result = awaitAll(transactionsDeferred, accountsDeferred)

                transactions = result[0] as? List<TransactionDB> ?: emptyList()
                accounts = result[1] as? List<AccountDB> ?: emptyList()
            }
        }
    }

    override suspend fun fetchTransactions(): List<TransactionDB> =
        withContext(dispatchers.backgroundThread()) {
            transactionsDao.getAllTransactions()
        }

    override suspend fun fetchAccounts(): List<AccountDB> =
        withContext(dispatchers.backgroundThread()) {
            accountsDao.getAllAccounts()
        }

    override suspend fun fetchMostValuableAccounts(): List<AccountDB> =
        withContext(dispatchers.backgroundThread()) {
            accounts.sortedByDescending { it.balance }.take(5)
        }

    override suspend fun fetchMostLargerTransactions(): List<TransactionDB> =
        withContext(dispatchers.backgroundThread()) {
            transactions.sortedByDescending { it.amount }.take(5)
        }

    override suspend fun fetchMostUsedAccounts(): Map<AccountDB, Int> =
        withContext(dispatchers.backgroundThread()) {
            val accountMap = accounts.associateWith { account ->
                transactions.count { it.accountTo == account.id || it.accountFrom == account.id }
            }

            accountMap.entries
                .sortedByDescending { it.value }
                .take(5)
                .associate { it.key to it.value }
        }

    override suspend fun fetchMostTrustedBanks(): Map<Bank, Double> =
        withContext(dispatchers.backgroundThread()) {
            accounts.groupBy { it.bank }
                .mapValues { (_, accounts) -> accounts.sumOf { it.balance } }
                .entries
                .sortedByDescending { it.value }
                .take(5)
                .associate { it.key to it.value }
        }


    override suspend fun fetchInvestmentProgress(): Map<String, Double> {
        val result = transactions
            .filter { it.type == TransactionType.INVESTMENT }
            .groupBy { it.date.formatToDateString().substring(3) }
            .mapValues { it.value.sumOf { transaction -> transaction.amount } }
            .toSortedMap(compareBy {
                val (month, year) = it.split("/")
                "$year$month" // Create a comparable string "yyyyMM"
            })
        return result
    }
}
