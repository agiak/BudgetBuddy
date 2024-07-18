package com.example.mywallet.features.profileModule.profile.domain

import com.example.core.data.User

interface ProfileRepository {

    suspend fun fetchUser(): User?
}