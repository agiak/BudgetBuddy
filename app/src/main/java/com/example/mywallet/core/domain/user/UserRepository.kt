package com.example.mywallet.core.domain.user

import com.example.mywallet.core.data.user.User

fun interface UserRepository {

    fun fetchUser(): User?
}