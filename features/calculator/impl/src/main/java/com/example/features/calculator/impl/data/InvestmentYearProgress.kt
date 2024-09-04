package com.example.features.calculator.impl.data

data class InvestmentYearProgress(
    val year: String,
    val amount: Double
)

data class InvestmentResult(
    val overview: InvestmentOverview? = null,
    val growthPerYear: List<InvestmentYearProgress> = emptyList()
)

data class InvestmentOverview(
    val finalNetworth: Double,
    val interestIncome: Double,
    val contribution: Double,
)