package com.example.mywallet.features.activation.quicklogin.domain

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.data.user.User
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.activation.guide.domain.GuideRepository.Companion.GUIDE_KEY
import com.example.mywallet.features.activation.quicklogin.data.UserState
import com.example.mywallet.features.activation.register.domain.RegisterRepository.Companion.USER_KEY
import com.example.mywallet.storage.domain.database.daos.AccountDao
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
