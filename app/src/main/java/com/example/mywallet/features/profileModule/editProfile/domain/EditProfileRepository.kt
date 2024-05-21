package com.example.mywallet.features.profileModule.editProfile.domain

import com.example.mywallet.core.data.user.User

interface EditProfileRepository {

    suspend fun fetchUser(): User?

    fun saveChanges(user: User)
}