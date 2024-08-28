package com.example.features.calculator.impl.domain

import com.example.core.domain.dispatchers.IDispatchers
import com.example.features.calculator.impl.data.InvestmentInfo
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers
) : CalculatorRepository {

    override suspend fun calculate(investmentInfo: InvestmentInfo) {
        withContext(dispatchers.backgroundThread()) {

        }
    }
}