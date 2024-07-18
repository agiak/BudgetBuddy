package com.example.mywallet.features.account.accounts.domain

import com.example.mywallet.features.account.accountsFilter.data.AccountFilterSelection
import com.example.core.storage.data.AccountDB
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    fun fetchAccounts(): Flow<List<AccountDB>>

    suspend fun applyFilters(filterSelection: AccountFilterSelection): List<AccountDB>
}