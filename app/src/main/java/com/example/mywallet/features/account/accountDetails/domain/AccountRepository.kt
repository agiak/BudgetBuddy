package com.example.mywallet.features.account.accountDetails.domain

import com.example.mywallet.features.account.accountDetails.data.AccountDetails
import com.example.mywallet.features.account.accountDetails.data.AccountStatics
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getAccountObservable(accountID: Long): Flow<AccountDB?>

    suspend fun fetchAccountDetails(accountID: Long): AccountDB?

    suspend fun fetchTransactions(accountID: Long): List<TransactionDB>

    suspend fun fetchAccountStatics(accountID: Long): AccountStatics?

    suspend fun updateAccount(accountInfo: AccountDetails)

    suspend fun deleteAccount(accountID: Long)
}