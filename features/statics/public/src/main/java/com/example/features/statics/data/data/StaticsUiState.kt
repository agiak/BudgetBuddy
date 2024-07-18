package com.example.features.statics.data.data

sealed class StaticsUiState {
    data object Idle : StaticsUiState()
    data object Loading : StaticsUiState()
    data class Result(val statsItems: List<StaticsItem>) : StaticsUiState()
    data class Error(val errorMessage: String) : StaticsUiState()
}