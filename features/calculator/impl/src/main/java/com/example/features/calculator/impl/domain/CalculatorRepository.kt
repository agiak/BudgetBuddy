package com.example.features.calculator.impl.domain

import com.example.features.calculator.impl.data.InvestmentInfo
import com.example.features.calculator.impl.data.InvestmentResult

interface CalculatorRepository {

    suspend fun calculate(investmentInfo: InvestmentInfo): InvestmentResult
}