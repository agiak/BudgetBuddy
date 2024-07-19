package com.example.features.statics.impl.data.data

sealed class StaticsItem {
    data class CommonStats(val commonStatCategories: List<CommonStatCategory>): StaticsItem()
}
