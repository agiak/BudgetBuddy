package com.example.mywallet.features.activation.register.domain

import com.example.mywallet.core.data.user.User

interface RegisterRepository {

    fun register(user: User)

    suspend fun hasAccounts(): Boolean

    companion object {
        const val USER_KEY = "user_key"
    }

}
