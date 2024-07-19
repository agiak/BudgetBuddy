package com.example.feature.register.impl.domain

import com.example.core.data.User

interface RegisterRepository {

    fun register(user: User)

    suspend fun hasAccounts(): Boolean

}
