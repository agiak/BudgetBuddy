package com.example.mywallet.features.profileModule.profile.domain

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.data.user.User
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.activation.register.domain.RegisterRepository.Companion.USER_KEY
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val dispatchers: IDispatchers,
): ProfileRepository {

    override suspend fun fetchUser(): User? =
        withContext(dispatchers.backgroundThread()) {
            preferenceManager.get(USER_KEY, null)
        }
}