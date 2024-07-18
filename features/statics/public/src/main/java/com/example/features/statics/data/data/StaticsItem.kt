package com.example.features.statics.data.data

sealed class StaticsItem {
    data class CommonStats(val commonStatCategories: List<CommonStatCategory>): StaticsItem()
}
