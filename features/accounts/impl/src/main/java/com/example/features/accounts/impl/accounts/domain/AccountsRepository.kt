package com.example.features.accounts.impl.accounts.domain

import com.example.core.storage.data.AccountDB
import com.example.features.accounts.impl.filters.data.AccountFilterSelection
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    fun fetchAccounts(): Flow<List<AccountDB>>

    suspend fun applyFilters(filterSelection: AccountFilterSelection): List<AccountDB>
}