package com.example.mywallet.features.statics.data

sealed class StaticsItem {
    data class CommonStats(val commonStatCategories: List<CommonStatCategory>): StaticsItem()
}
