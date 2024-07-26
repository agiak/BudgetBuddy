package com.example.features.transactionsViaFile.impl.transactionsSelection.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.daos.TransactionDao
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