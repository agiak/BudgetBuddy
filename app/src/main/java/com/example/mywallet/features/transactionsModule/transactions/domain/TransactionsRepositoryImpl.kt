package com.example.mywallet.features.transactionsModule.transactions.domain

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.storage.data.TransactionDB
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val dao: TransactionDao
): TransactionsRepository {

    override fun fetchTransactions(): Flow<List<TransactionDB>> = dao.getAllTransactionsObservable() ?: flowOf(
        emptyList()
    )

    override suspend fun deleteTransaction(transactionID: Long) =
        withContext(dispatchers.backgroundThread()) {
            dao.deleteTransactionById(transactionID)
        }
}