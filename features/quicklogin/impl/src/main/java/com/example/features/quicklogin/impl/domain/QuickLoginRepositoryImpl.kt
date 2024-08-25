package com.example.features.quicklogin.impl.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.domain.user.UserRepository
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.features.quicklogin.impl.data.UserState
import javax.inject.Inject

class QuickLoginRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val preferenceManager: PreferenceManager,
    private val dispatchers: IDispatchers,
    private val userRepository: UserRepository,
): QuickLoginRepository {

    override suspend fun getUserState(): UserState =
        with(dispatchers.backgroundThread()) {
            when {
                !isGuideDisplayed() -> UserState.Uneducated
                !isRegistered() -> UserState.Unregister
                !accountDao.hasAccounts() -> UserState.Unsetted
                else -> UserState.Valid
            }
        }

    private fun isGuideDisplayed(): Boolean = preferenceManager.get("guide_key", false)
    private fun isRegistered(): Boolean = userRepository.fetchUser() != null

}
