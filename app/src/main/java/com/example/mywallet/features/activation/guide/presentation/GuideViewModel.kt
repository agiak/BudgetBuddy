package com.example.mywallet.features.activation.guide.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.features.accountModule.account.data.AccountTransaction
import com.example.mywallet.features.accountModule.account.data.toAccountTransactions
import com.example.mywallet.features.accountModule.account.domain.AccountRepository
import com.example.mywallet.features.activation.guide.domain.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor(
    private val repository: GuideRepository,
): ViewModel() {

    fun setGuideAsDisplayed() {
        repository.setGuideToDisplayed()
    }

}