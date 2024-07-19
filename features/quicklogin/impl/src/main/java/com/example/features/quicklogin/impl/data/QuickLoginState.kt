package com.example.features.quicklogin.impl.data

sealed class QuickLoginState {
    data object Loading: QuickLoginState()
    data class Result(val userState: UserState): QuickLoginState()

    data class Error(val errorMessage: String): QuickLoginState()
}