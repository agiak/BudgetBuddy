package com.example.mywallet.features.activation.guide.presentation

import androidx.lifecycle.ViewModel
import com.example.mywallet.features.activation.guide.domain.GuideRepository
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