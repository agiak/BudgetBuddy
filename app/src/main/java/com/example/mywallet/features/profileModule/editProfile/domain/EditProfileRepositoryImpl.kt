package com.example.mywallet.features.profileModule.editProfile.domain

import com.example.core.data.User
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.domain.user.UserRepository.Companion.USER_KEY
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val dispatchers: IDispatchers,
): EditProfileRepository {

    override suspend fun fetchUser(): User? =
        withContext(dispatchers.backgroundThread()) {
            preferenceManager.get(USER_KEY, null)
        }

    override fun saveChanges(user: User) {
        preferenceManager.put(USER_KEY, user)
    }
}