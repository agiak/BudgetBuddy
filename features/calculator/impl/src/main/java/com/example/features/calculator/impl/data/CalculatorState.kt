package com.example.features.calculator.impl.data

import com.example.core.data.error.UiText

data class CalculatorState(
    val isLoading: Boolean = false,
    val data: List<InvestmentResult> = emptyList(),
    val error: UiText? = null
)
