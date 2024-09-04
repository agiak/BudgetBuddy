package com.example.features.calculator.impl.domain

import com.example.common.myutils.roundToTwoDecimal
import com.example.core.domain.dispatchers.IDispatchers
import com.example.features.calculator.impl.data.InvestmentInfo
import com.example.features.calculator.impl.data.InvestmentOverview
import com.example.features.calculator.impl.data.InvestmentResult
import com.example.features.calculator.impl.data.InvestmentYearProgress
import kotlinx.coroutines.withContext
import java.time.Year
import javax.inject.Inject
import kotlin.math.pow

class CalculatorRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers
) : CalculatorRepository {

    override suspend fun calculate(investmentInfo: InvestmentInfo): InvestmentResult =
        withContext(dispatchers.backgroundThread()) {

            var totalAmount = 0.0
            val currentYear = Year.now().value
            val results = ArrayList<InvestmentYearProgress>()

            for (year in 1..investmentInfo.duration) {
                totalAmount += if (investmentInfo.isYearEndInvest) {
                    investmentInfo.amount * (1 + investmentInfo.rate).pow((investmentInfo.duration - year).toDouble())
                } else {
                    investmentInfo.amount * (1 + investmentInfo.rate).pow(year.toDouble())
                }
                val investmentYearProgress = InvestmentYearProgress(
                    year = (currentYear + year).toString(),
                    amount = totalAmount.roundToTwoDecimal()
                )
                results.add(investmentYearProgress)
            }

            if (results.isNotEmpty()) {
                val finalNetWorth = results.last().amount
                val totalContribution = investmentInfo.amount * investmentInfo.duration
                val interestIncome = finalNetWorth - totalContribution
                InvestmentResult(
                    overview = InvestmentOverview(
                        finalNetworth = finalNetWorth,
                        interestIncome = interestIncome,
                        contribution = totalContribution
                    ),
                    growthPerYear = results
                )
            } else {
                InvestmentResult()
            }

        }
}