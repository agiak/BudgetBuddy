package com.example.mywallet.features.accountModule.accountAdd.domain

import com.example.mywallet.core.data.common.TransactionType
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
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