package com.example.features.statics.impl.data.data

sealed class StaticsItem {
    data class CommonStats(val commonStatCategories: List<CommonStatCategory>): StaticsItem()
    data class InvestmentProgress(val progress: Map<String, Double>): StaticsItem()
    data object EmptyStats: StaticsItem()
}
