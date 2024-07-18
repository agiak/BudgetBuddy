package com.example.mywallet.features.account.accountAdd.domain

import com.example.core.data.common.TransactionType
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddAccountRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val dao: AccountDao,
    private val transactionsDao: TransactionDao
) : AddAccountRepository {

    override suspend fun addAccount(accountDB: AccountDB): Unit =
        withContext(dispatchers.backgroundThread()) {
            dao.insertAccount(accountDB)
            dao.getAccountByName(accountDB.name)?.let { transactionsDao.insertTransaction(it.getInitialTransaction())}
        }

    // Every time user creates a new account insert an initial transaction as income transaction for stats reason
    private fun AccountDB.getInitialTransaction() = TransactionDB(
        date = this.createdDate,
        amount = this.balance,
        type = TransactionType.INCOME,
        accountFrom = this.id,
        description = "Account creation",
        bankFromIcon = this.bank.drawableID,
        accountFromName = this.name
    )
}