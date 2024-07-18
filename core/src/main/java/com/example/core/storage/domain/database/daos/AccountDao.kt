package com.example.core.storage.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.account.AccountDetails
import com.example.core.data.bank.Bank
import com.example.core.storage.data.AccountDB
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts")
    fun getAllAccountsObservable(): Flow<List<AccountDB>>?

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountDB>

    @Query("SELECT SUM(balance) FROM accounts")
    fun getTotalBalance(): Flow<Double>?

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getAccount(accountId: Long): AccountDB?

    @Query("SELECT * FROM accounts WHERE name = :accountName")
    suspend fun getAccountByName(accountName: String): AccountDB?

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountObservable(accountId: Long): Flow<AccountDB?>

    @Insert
    suspend fun insertAccount(account: AccountDB)

    @Update
    suspend fun updateAccount(account: AccountDB)

    @Query("UPDATE accounts SET name = :name, balance = :balance, bank = :bank WHERE id = :id")
    fun updateAccountDetails(id: Long, name: String, balance: String, bank: Bank)

    fun updateAccountDetails(accountDetails: AccountDetails) {
        updateAccountDetails(
            id = accountDetails.id,
            name = accountDetails.name,
            balance = accountDetails.balance,
            bank = accountDetails.bank
        )
    }

    @Delete
    suspend fun deleteAccount(account: AccountDB)

    @Query("DELETE FROM accounts WHERE id = :accountId")
    suspend fun deleteAccountById(accountId: Long)

    @Query("SELECT COUNT(*) > 0 FROM accounts")
    suspend fun hasAccounts(): Boolean
}
