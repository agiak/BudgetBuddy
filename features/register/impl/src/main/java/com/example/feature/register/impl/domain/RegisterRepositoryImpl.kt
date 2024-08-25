package com.example.feature.register.impl.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val accountsDao: AccountDao,
    private val dispatchers: IDispatchers
): RegisterRepository {

    override suspend fun hasAccounts(): Boolean =
        withContext(dispatchers.backgroundThread()) {
            accountsDao.hasAccounts()
        }

}
