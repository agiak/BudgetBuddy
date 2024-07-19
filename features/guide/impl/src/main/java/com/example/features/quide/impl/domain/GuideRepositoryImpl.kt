package com.example.features.quide.impl.domain

import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.features.quide.impl.domain.GuideRepository.Companion.GUIDE_KEY
import javax.inject.Inject

class GuideRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
): GuideRepository {

    override fun setGuideToDisplayed() {
        preferenceManager.put(GUIDE_KEY, true)
    }
}
