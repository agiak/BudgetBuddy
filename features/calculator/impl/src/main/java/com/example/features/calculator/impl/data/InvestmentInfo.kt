package com.example.features.calculator.impl.data

import com.agcoding.features.calculator.impl.R
import kotlin.math.pow

data class InvestmentInfo(
    val amount: Double,
    val duration: Int,
    val rate: Double,
    val period: Period = Period.YEARLY,
    val isYearEndInvest: Boolean = false
)

enum class Period(val description: Int) {
    MONTHLY(R.string.investment_repeat_period_monthly),
    YEARLY(R.string.investment_repeat_period_yearly),
}

fun calculateInvestmentGrowth(investmentInfo: InvestmentInfo): Double {
    var totalAmount = 0.0
    for (year in 1..investmentInfo.duration) {
        totalAmount += investmentInfo.amount * (1 + investmentInfo.rate).pow(year.toDouble())
    }
    return totalAmount
}

fun calculateInvestmentGrowthEndOfYear(investmentInfo: InvestmentInfo): Double {
    var totalAmount = 0.0
    for (year in 1..investmentInfo.duration) {
        // Since the investment is made at the end of the year, it doesn't earn interest until the next year.
        totalAmount += investmentInfo.amount * Math.pow(1 + investmentInfo.rate, (investmentInfo.duration - year).toDouble())
    }
    return totalAmount
}

fun main() {
    val investment = InvestmentInfo(amount = 1200.0, duration = 5, rate = 0.05)
    val totalGrowth = calculateInvestmentGrowthEndOfYear(investment)
    println("Total investment growth after ${investment.duration} years: $totalGrowth")
}