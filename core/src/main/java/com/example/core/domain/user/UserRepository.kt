package com.example.core.domain.user

import com.example.core.data.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    val user: StateFlow<User?>

    fun fetchUser(): User?

    fun updateUser(user: User)

    companion object {
        const val USER_KEY = "user_key"
    }
}