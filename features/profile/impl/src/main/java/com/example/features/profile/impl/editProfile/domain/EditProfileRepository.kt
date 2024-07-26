package com.example.features.profile.impl.editProfile.domain

import com.example.core.data.User

interface EditProfileRepository {

    suspend fun fetchUser(): User?

    fun saveChanges(user: User)
}