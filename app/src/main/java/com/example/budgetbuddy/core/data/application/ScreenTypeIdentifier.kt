package com.example.budgetbuddy.core.data.application

import com.agcoding.budgetbuddy.R

fun Int.identifyScreenType(): ScreenType =
    when {
        menuScreens.contains(this) -> ScreenType.MENU
        else -> ScreenType.INSIDE
    }

private val menuScreens = listOf(
    R.id.navigation_home,
    R.id.navigation_statics,
    com.agcoding.features.transactions.R.id.navigation_transactions,
    com.agcoding.features.more.R.id.navigation_more,
)