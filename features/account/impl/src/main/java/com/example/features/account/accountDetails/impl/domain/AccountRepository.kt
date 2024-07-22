package com.example.features.account.accountDetails.impl.domain

import com.example.core.data.account.AccountDetails
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountStatics
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.TransactionDB
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getAccountObservable(accountID: Long): Flow<AccountDB?>

    suspend fun fetchAccountDetails(accountID: Long): AccountDB?

    suspend fun fetchTransactions(accountID: Long): List<TransactionDB>

    suspend fun fetchAccountStatics(accountID: Long): AccountStatics?

    suspend fun updateAccount(accountInfo: AccountDetails)

    suspend fun deleteAccount(accountID: Long)
}