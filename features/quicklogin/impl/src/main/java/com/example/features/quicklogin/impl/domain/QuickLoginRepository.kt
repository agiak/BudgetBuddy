package com.example.features.quicklogin.impl.domain

import com.example.features.quicklogin.impl.data.UserState

fun interface QuickLoginRepository {

    suspend fun getUserState(): UserState
}