package com.example.mywallet.features.home.domain

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val transactionsDao: TransactionDao,
    private val dispatchers: IDispatchers,
) : HomeRepository {

    override fun fetchAccounts(): Flow<List<AccountDB>> = accountDao.getAllAccountsObservable() ?: flowOf(
        emptyList()
    )
    override fun fetchLastTransactions(): Flow<List<TransactionDB>> =
        transactionsDao.getLastTransactions() ?: flowOf(emptyList())

    override fun fetchTotalBalance(): Flow<Double> = accountDao.getTotalBalance() ?: flowOf(0.0)

    override suspend fun deleteAccount(accountID: Long) =
        withContext(dispatchers.backgroundThread()) {
            accountDao.deleteAccountById(accountId = accountID)
        }

}
