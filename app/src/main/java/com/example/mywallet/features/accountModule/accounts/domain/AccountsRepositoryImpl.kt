package com.example.mywallet.features.accountModule.accounts.domain

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.domain.database.daos.AccountDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val accountsDao: AccountDao,
    private val dispatchers: IDispatchers,
): AccountsRepository {

    override fun fetchAccounts(): Flow<List<AccountDB>> = accountsDao.getAllAccountsObservable() ?: flowOf(
        emptyList()
    )

}