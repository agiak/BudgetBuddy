package com.example.features.calculator.impl.data

import com.example.features.calculator.impl.R

data class InvestmentInfo(
    val amount: Double,
    val duration: Int,
    val rate: Double,
    val period: Period = Period.YEARLY
)


enum class Period(val description: Int) {
    MONTHLY(R.string.investment_repeat_period_monthly),
    YEARLY(R.string.investment_repeat_period_yearly),
}