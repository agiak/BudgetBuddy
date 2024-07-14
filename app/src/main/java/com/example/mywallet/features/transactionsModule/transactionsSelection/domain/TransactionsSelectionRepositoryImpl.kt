package com.example.mywallet.features.transactionsModule.transactionsSelection.domain

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.storage.data.TransactionDB
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsSelectionRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val transactionsDao: TransactionDao,
) : TransactionsSelectionRepository {

    override suspend fun saveTransactions(transactions: MutableMap<TransactionDB, Boolean>): Int =
        withContext(dispatchers.backgroundThread()) {
            val selectedTransactions: List<TransactionDB> = transactions
                .filter { it.value }
                .map { it.key }

            selectedTransactions.forEach { transaction ->
                transactionsDao.insertTransaction(transaction)
            }
            selectedTransactions.size
        }

}