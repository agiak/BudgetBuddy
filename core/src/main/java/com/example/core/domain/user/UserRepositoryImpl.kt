package com.example.core.domain.user

import com.example.core.data.User
import com.example.core.domain.user.UserRepository.Companion.USER_KEY
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
): UserRepository {

    override fun fetchUser(): User? = preferenceManager.get(USER_KEY, null)

}
