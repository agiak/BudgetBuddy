package com.example.core.storage.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.common.TransactionType
import com.example.core.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    fun getAllTransactionsObservable(): Flow<List<TransactionDB>>

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): List<TransactionDB>

    @Query("SELECT * FROM transactions WHERE accountFrom = :accountId OR accountTo = :accountId")
    fun getTransactionsForAccountObservable(accountId: Long): Flow<List<TransactionDB>>?

    @Query("SELECT * FROM transactions WHERE accountFrom = :accountId OR accountTo = :accountId")
    suspend fun getTransactionsForAccount(accountId: Long): List<TransactionDB>

    @Query("SELECT * FROM transactions WHERE accountFrom = :accountId AND type = :type")
    suspend fun getTransactionsByType(
        accountId: Long,
        type: TransactionType
    ): List<TransactionDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionDB)

    @Update
    suspend fun updateTransaction(transaction: TransactionDB)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionDB)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: Long)

    @Query("SELECT * FROM transactions ORDER BY date DESC LIMIT 3")
    fun getLastTransactions(): Flow<List<TransactionDB>>?
}
