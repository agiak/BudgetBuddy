package com.example.mywallet.features.activation.quicklogin.domain

import com.example.mywallet.features.activation.quicklogin.data.UserState

fun interface QuickLoginRepository {

    suspend fun getUserState(): UserState
}