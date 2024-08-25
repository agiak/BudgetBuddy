package com.example.feature.register.impl.domain

interface RegisterRepository {

    suspend fun hasAccounts(): Boolean

}
