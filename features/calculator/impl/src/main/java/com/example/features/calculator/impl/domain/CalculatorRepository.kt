package com.example.features.calculator.impl.domain

import com.example.features.calculator.impl.data.InvestmentInfo

interface CalculatorRepository {

    suspend fun calculate(investmentInfo: InvestmentInfo)
}