package com.example.mywallet.features.home.data

sealed class HomeState {
    data object Loading: HomeState()
    data class Error(val errorMessage: String): HomeState()
    data class Result(val homeItems: List<HomeItem>): HomeState()
}