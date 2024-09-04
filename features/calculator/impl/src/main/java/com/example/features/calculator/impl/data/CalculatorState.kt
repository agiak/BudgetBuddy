package com.example.features.calculator.impl.data

import com.example.core.data.error.UiText

data class CalculatorState(
    val isLoading: Boolean = false,
    val data: InvestmentResult? = null,
    val error: UiText? = null
)
