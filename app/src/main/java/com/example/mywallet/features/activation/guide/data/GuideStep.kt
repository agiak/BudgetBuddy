package com.example.mywallet.features.activation.guide.data

import com.example.mywallet.R

enum class GuideStep(val description: String, val iconId: Int) {
    SUMMARY("Keep track your money", R.drawable.ic_logo),
    TRANSACTIONS("Move money from pocket to pocket", R.drawable.ic_logo),
    STATICS("See analyzed stats for your accounts", R.drawable.ic_logo),
}