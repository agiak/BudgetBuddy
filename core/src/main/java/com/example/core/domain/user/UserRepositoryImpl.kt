package com.example.core.domain.user

import com.example.core.data.User
import com.example.core.domain.user.UserRepository.Companion.USER_KEY
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
): UserRepository {

    private val _user = MutableStateFlow<User?>(null)
    override val user: StateFlow<User?> = _user

    override fun fetchUser(): User? {
        val user = preferenceManager.get(USER_KEY, null) as User?
        _user.value = user
        return user
    }

    override fun updateUser(user: User) {
        preferenceManager.put(USER_KEY, user)
        _user.value = user
    }
}
