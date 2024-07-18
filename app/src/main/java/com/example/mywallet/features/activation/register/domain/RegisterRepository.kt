package com.example.mywallet.features.activation.register.domain

import com.example.core.data.User

interface RegisterRepository {

    fun register(user: User)

    suspend fun hasAccounts(): Boolean

}
