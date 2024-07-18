package com.example.mywallet.features.profileModule.editProfile.domain

import com.example.core.data.User

interface EditProfileRepository {

    suspend fun fetchUser(): User?

    fun saveChanges(user: User)
}