package com.example.features.accounts.impl.accounts.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.AccountDB
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.features.accounts.impl.filters.data.AccountFilterSelection
import com.example.features.accounts.impl.filters.data.AccountsFilterGroupBy
import com.example.features.accounts.impl.filters.data.AccountsFilterOrderBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val accountsDao: AccountDao,
    private val dispatchers: IDispatchers,
) : AccountsRepository {

    override fun fetchAccounts(): Flow<List<AccountDB>> =
        accountsDao.getAllAccountsObservable()

    override suspend fun applyFilters(filterSelection: AccountFilterSelection): List<AccountDB> =
        withContext(dispatchers.backgroundThread()) {
            var accounts = accountsDao.getAllAccounts()

            filterSelection.orderBy?.let { accounts = accounts.orderByFilter(it) }

            filterSelection.groupBy?.let { accounts = accounts.groupByFilter(it) }

            accounts
        }

    private fun List<AccountDB>.groupByFilter(groupBy: AccountsFilterGroupBy): List<AccountDB> {
        return when (groupBy) {
            AccountsFilterGroupBy.BY_BANK -> groupBy { it.bank.name }.flatMap { it.value }
        }
    }

    private fun List<AccountDB>.orderByFilter(orderBy: AccountsFilterOrderBy): List<AccountDB> {
        return when (orderBy) {
            AccountsFilterOrderBy.DESCENDING -> sortedByDescending { it.balance }
            AccountsFilterOrderBy.ASCENDING -> sortedBy { it.balance }
            AccountsFilterOrderBy.DATE_ADDED -> sortedBy { it.id }
        }
    }
}
