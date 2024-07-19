package com.example.features.quicklogin.impl.data

sealed class UserState {
    data object Unregister: UserState()
    data object Uneducated: UserState()
    data object Unsetted: UserState()
    data object Valid: UserState()
}