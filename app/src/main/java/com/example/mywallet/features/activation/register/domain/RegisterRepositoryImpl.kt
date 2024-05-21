package com.example.mywallet.features.activation.register.domain

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.data.user.User
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.activation.register.domain.RegisterRepository.Companion.USER_KEY
import com.example.mywallet.storage.domain.database.daos.AccountDao
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
