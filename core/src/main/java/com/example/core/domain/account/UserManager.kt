package com.example.core.domain.account

import com.example.core.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserManager {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun updateUser(user: User) {
        _user.value = user
    }
}