package com.example.budgetbuddy.core.data.application

import com.budgetbuddy.R

fun Int.identifyScreenType(): ScreenType =
    when {
        menuScreens.contains(this) -> ScreenType.MENU
        else -> ScreenType.INSIDE
    }

private val menuScreens = listOf(
    R.id.navigation_home,
    R.id.navigation_statics,
    com.example.features.transactions.R.id.navigation_transactions,
    com.example.features.more.R.id.navigation_more,
)