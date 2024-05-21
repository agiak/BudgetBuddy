package com.example.mywallet.features.activation.quicklogin.data

sealed class UserState {
    data object Unregister: UserState()
    data object Uneducated: UserState()
    data object Unsetted: UserState()
    data object Valid: UserState()
}