package com.example.features.statics.impl.data.data

sealed class StaticsItem {
    data class CommonStats(val commonStatCategories: List<CommonStatCategory>) : StaticsItem()
    data class InvestmentProgress(val progress: Map<String, Double>) : StaticsItem()
    data object EmptyStats : StaticsItem()
}

fun mergeCommonStats(list1: List<StaticsItem>, list2: List<StaticsItem>): List<StaticsItem> {
    val mergedCategories = mergeCategories(list1, list2)
    return if (mergedCategories.isNotEmpty()) {
        listOf(StaticsItem.CommonStats(mergedCategories)) + getOtherItems(list1) + getOtherItems(
            list2
        )
    } else {
        getOtherItems(list1) + getOtherItems(list2)
    }
}

private fun mergeCategories(
    list1: List<StaticsItem>,
    list2: List<StaticsItem>
): List<CommonStatCategory> {
    val mergedCategories = mutableListOf<CommonStatCategory>()
    getCommonStats(list1).forEach { mergedCategories.addAll(it.commonStatCategories) }
    getCommonStats(list2).forEach { mergedCategories.addAll(it.commonStatCategories) }
    return mergedCategories
}

private fun getCommonStats(list: List<StaticsItem>): List<StaticsItem.CommonStats> =
    list.filterIsInstance<StaticsItem.CommonStats>()

private fun getOtherItems(list: List<StaticsItem>): List<StaticsItem> =
    list.filter { it !is StaticsItem.CommonStats }