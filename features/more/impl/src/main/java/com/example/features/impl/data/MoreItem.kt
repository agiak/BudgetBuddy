package com.example.features.impl.data

import com.agcoding.features.impl.R

enum class MoreItem(val icon: Int, val label: Int) {
    Rule(R.drawable.ic_rules, R.string.more_rule),
    StockMarket(R.drawable.ic_stocks, R.string.more_stock_market),
    InvestmentCalculator(R.drawable.ic_calculator, R.string.more_investment_calculator)
}
