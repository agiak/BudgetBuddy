package com.example.budgetbuddy.features.home.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val transactionsDao: TransactionDao,
    private val dispatchers: IDispatchers,
) : HomeRepository {

    override fun fetchAccounts(): Flow<List<AccountDB>> = accountDao.getAllAccountsObservable()
    override fun fetchLastTransactions(): Flow<List<TransactionDB>> =
        transactionsDao.getLastTransactions() ?: flowOf(emptyList())

    override fun fetchTotalBalance(): Flow<Double> = accountDao.getTotalBalance() ?: flowOf(0.0)

    override suspend fun deleteAccount(accountID: Long) =
        withContext(dispatchers.backgroundThread()) {
            accountDao.deleteAccountById(accountId = accountID)
        }

}
