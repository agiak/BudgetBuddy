package com.example.mywallet.features.activation.quicklogin.domain

import com.example.core.data.User
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.domain.user.UserRepository.Companion.USER_KEY
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.mywallet.features.activation.guide.domain.GuideRepository.Companion.GUIDE_KEY
import com.example.mywallet.features.activation.quicklogin.data.UserState
import javax.inject.Inject

class QuickLoginRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val preferenceManager: PreferenceManager,
    private val dispatchers: IDispatchers,
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

    private fun isGuideDisplayed(): Boolean = preferenceManager.get(GUIDE_KEY, false)
    private fun isRegistered(): Boolean =
        preferenceManager.get(USER_KEY, null) as? User? != null

}
