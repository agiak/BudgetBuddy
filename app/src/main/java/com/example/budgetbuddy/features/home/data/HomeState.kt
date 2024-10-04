package com.example.budgetbuddy.features.home.data

sealed class HomeState {
    data object Loading: HomeState()
    data class Error(val errorMessage: String): HomeState()
    data class Result(val homeItems: List<HomeItem>): HomeState()
}