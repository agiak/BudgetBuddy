package com.example.features.impl.data

import com.example.features.impl.R

enum class MoreItem(val icon: Int, val label: Int) {
    Rule(com.example.core.R.drawable.ic_logo, R.string.more_rule),
    StockMarket(com.example.core.R.drawable.ic_logo, R.string.more_stock_market),
    InvestmentCalculator(R.drawable.ic_calculator, R.string.more_investment_calculator)
}
