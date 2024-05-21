package com.example.mywallet.features.activation.quicklogin.data

sealed class QuickLoginState {
    data object Loading: QuickLoginState()
    data class Result(val userState: UserState): QuickLoginState()

    data class Error(val errorMessage: String): QuickLoginState()
}