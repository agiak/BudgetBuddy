package com.example.mywallet.core.data.application

import com.example.mywallet.R

fun Int.identifyScreenType(): ScreenType =
    when {
        menuScreens.contains(this) -> ScreenType.MENU
        else -> ScreenType.INSIDE
    }

private val menuScreens = listOf(
    R.id.navigation_home,
    R.id.navigation_statics,
    com.example.features.transactions.R.id.navigation_transactions
)