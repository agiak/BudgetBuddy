package com.example.features.quide.impl.data

import com.example.features.quide.impl.R

enum class GuideStep(val description: Int, val iconId: Int) {
    SUMMARY(R.string.guide_step1, com.example.core.R.drawable.ic_logo),
    TRANSACTIONS(R.string.guide_step2, com.example.core.R.drawable.ic_logo),
    STATICS(R.string.guide_step3, com.example.core.R.drawable.ic_logo),
}