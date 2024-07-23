package com.example.mywallet.features.rulesModule.ruleSalary.domain

import com.example.core.data.common.TransactionType
import com.example.core.data.common.isInternalTransaction
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SalaryTransactionRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val transactionDao: TransactionDao,
    private val accountsDao: AccountDao,
) : SalaryTransactionRepository {

    override suspend fun addTransaction(transactionDB: TransactionDB, applyTransaction: Boolean) =
        withContext(dispatchers.backgroundThread()) {
            if (!applyTransaction) {
                transactionDao.insertTransaction(transactionDB)
            } else {
                executeTransaction(transactionDB)
            }
        }

    private suspend fun executeTransaction(transactionDB: TransactionDB) {
        when {
            transactionDB.type.isInternalTransaction() -> executeInternalTransaction(transactionDB)
            else -> executeOneAccountTransaction(transactionDB)
        }
    }

    private suspend fun executeOneAccountTransaction(transactionDB: TransactionDB) {

        // Adjust the transaction amount for expenses
        val amount =
            if (transactionDB.type == TransactionType.OUTCOME) -transactionDB.amount else transactionDB.amount

        // Retrieve the account information
        val accountFrom = accountsDao.getAccount(transactionDB.accountFrom)
        requireNotNull(accountFrom) { "Account with id ${transactionDB.accountFrom} not found" }

        // Update the account balance
        val newBalance = accountFrom.balance + amount
        accountsDao.updateAccount(accountFrom.copy(balance = newBalance))

        // Insert the transaction into the database
        transactionDao.insertTransaction(transactionDB.copy(amount = amount))
    }

    private suspend fun executeInternalTransaction(transactionDB: TransactionDB) {

        // Retrieve and update accountFrom
        val accountFrom = accountsDao.getAccount(transactionDB.accountFrom)
        val updatedAccountFrom =
            accountFrom!!.copy(balance = accountFrom.balance - transactionDB.amount)
        accountsDao.updateAccount(updatedAccountFrom)

        // Retrieve and update accountTo
        val accountTo = accountsDao.getAccount(transactionDB.accountTo!!)
        val updatedAccountTo =
            accountTo!!.copy(balance = accountTo.balance + transactionDB.amount)
        accountsDao.updateAccount(updatedAccountTo)

        // Insert the transaction into the database
        transactionDao.insertTransaction(transactionDB)
    }
}