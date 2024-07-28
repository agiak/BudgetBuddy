package com.example.features.quide.impl.data

import com.example.features.quide.impl.R

enum class GuideStep(val description: Int, val iconId: Int) {
    SUMMARY(R.string.guide_step1, R.drawable.ic_guide_track_your_money),
    TRANSACTIONS(R.string.guide_step2, R.drawable.ic_guide_transfer_money),
    STATICS(R.string.guide_step3, R.drawable.ic_guide_stats),
}