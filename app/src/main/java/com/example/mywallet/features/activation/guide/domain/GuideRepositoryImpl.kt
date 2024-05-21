package com.example.mywallet.features.activation.guide.domain

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.features.activation.guide.domain.GuideRepository.Companion.GUIDE_KEY
import javax.inject.Inject

class GuideRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
): GuideRepository {

    override fun setGuideToDisplayed() {
        preferenceManager.put(GUIDE_KEY, true)
    }
}