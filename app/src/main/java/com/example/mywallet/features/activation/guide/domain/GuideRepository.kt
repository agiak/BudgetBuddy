package com.example.mywallet.features.activation.guide.domain

fun interface GuideRepository {

    fun setGuideToDisplayed()

    companion object {
        val GUIDE_KEY = "guide_key"
    }
}