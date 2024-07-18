package com.example.mywallet.features.activation.register.domain

import com.example.core.data.User
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.domain.user.UserRepository.Companion.USER_KEY
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val accountsDao: AccountDao,
    private val dispatchers: IDispatchers
): RegisterRepository {

    override fun register(user: User) {
        preferenceManager.put(USER_KEY, user)
    }

    override suspend fun hasAccounts(): Boolean =
        withContext(dispatchers.backgroundThread()) {
            accountsDao.hasAccounts()
        }

}
