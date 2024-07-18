package com.example.core.domain.user

import com.example.core.data.User

fun interface UserRepository {

    fun fetchUser(): User?

    companion object {
        const val USER_KEY = "user_key"
    }
}