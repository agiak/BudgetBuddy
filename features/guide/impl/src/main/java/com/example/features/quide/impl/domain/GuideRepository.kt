package com.example.features.quide.impl.domain

fun interface GuideRepository {

    fun setGuideToDisplayed()

    companion object {
        val GUIDE_KEY = "guide_key"
    }
}
