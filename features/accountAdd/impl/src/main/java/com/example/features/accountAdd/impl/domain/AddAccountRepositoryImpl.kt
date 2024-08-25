package com.example.features.accountAdd.impl.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.AccountDB
import com.example.core.storage.domain.database.daos.AccountDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddAccountRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val dao: AccountDao,
) : AddAccountRepository {

    override suspend fun addAccount(accountDB: AccountDB): Unit =
        withContext(dispatchers.backgroundThread()) {
            dao.insertAccount(accountDB)
        }
}