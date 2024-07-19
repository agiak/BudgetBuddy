package com.example.feature.quide.presentation

import androidx.lifecycle.ViewModel
import com.example.features.quide.impl.domain.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor(
    private val repository: GuideRepository,
): ViewModel() {

    fun setGuideAsDisplayed() {
        repository.setGuideToDisplayed()
    }

}