package com.example.features.transactions.impl.transactions.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val dao: TransactionDao
) : TransactionsRepository {

    override fun fetchTransactions(): Flow<List<TransactionDB>> = dao.getAllTransactionsObservable()

    override suspend fun deleteTransaction(transactionID: Long) =
        withContext(dispatchers.backgroundThread()) {
            dao.deleteTransactionById(transactionID)
        }

    override suspend fun deleteTransactions() =
        withContext(dispatchers.backgroundThread()) {
            dao.deleteAllTransactions()
        }
}
