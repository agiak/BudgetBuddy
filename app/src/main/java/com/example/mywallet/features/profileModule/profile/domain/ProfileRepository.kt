package com.example.mywallet.features.profileModule.profile.domain

import com.example.mywallet.core.data.user.User

interface ProfileRepository {

    suspend fun fetchUser(): User?
}