package com.example.features.transactionAdd.impl.data

import com.example.core.data.error.UiText

sealed class AddTransactionUiState {
    data object Idle : AddTransactionUiState()
    data object Loading : AddTransactionUiState()
    data object Success : AddTransactionUiState()
    data class Error(val message: UiText) : AddTransactionUiState()
}