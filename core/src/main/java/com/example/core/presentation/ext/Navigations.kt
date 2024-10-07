package com.example.core.presentation.ext

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.agcoding.common.R

fun getNextScreenNavOptions() = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateFromBottom(destinationId: Int) {
    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.bottom_enter)
        .setExitAnim(R.anim.bottom_exit)
        .setPopEnterAnim(R.anim.bottom_enter)
        .setPopExitAnim(R.anim.bottom_exit)
        .build()
    this.navigate(destinationId, null, navOptions)
}

fun getPreviousScreenNavOptions() = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_left)
    .setExitAnim(R.anim.slide_out_right)
    .setPopEnterAnim(R.anim.slide_in_right)
    .setPopExitAnim(R.anim.slide_out_left)
    .build()
