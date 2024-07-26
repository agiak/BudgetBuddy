package com.example.features.profile.impl.profileOptions.domain

import com.example.core.data.User

interface ProfileRepository {

    suspend fun fetchUser(): User?
}