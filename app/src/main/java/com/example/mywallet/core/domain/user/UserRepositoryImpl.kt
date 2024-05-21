package com.example.mywallet.core.domain.user

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.data.user.User
import com.example.mywallet.features.activation.register.domain.RegisterRepository.Companion.USER_KEY
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
): UserRepository {

    override fun fetchUser(): User? = preferenceManager.get(USER_KEY, null)

}
