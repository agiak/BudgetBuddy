package com.example.mywallet.core.data.application

import com.example.mywallet.R

fun Int.identifyScreenType(): ScreenType =
    when {
        menuScreens.contains(this) -> ScreenType.MENU
        modalScreens.contains(this) -> ScreenType.MODAL
        fullScreens.contains(this) -> ScreenType.FULL_SCREEN
        bottomSheetScreens.contains(this) -> ScreenType.BOTTOM_SHEET
        else -> ScreenType.INSIDE
    }

private val menuScreens = listOf(R.id.navigation_home, R.id.navigation_statics, R.id.navigation_transactions)
private val modalScreens = listOf(R.id.navigation_profile)
private val fullScreens = listOf(R.id.navigation_account)
private val bottomSheetScreens = listOf(R.id.navigation_edit_account)