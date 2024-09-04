package com.example.features.calculator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.error.UiText
import com.example.features.calculator.impl.data.CalculatorState
import com.example.features.calculator.impl.data.InvestmentInfo
import com.example.features.calculator.impl.domain.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val repository: CalculatorRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    fun calculate(investmentInfo: InvestmentInfo) {
        viewModelScope.launch {
            runCatching {
                _state.update { it.copy(isLoading = true) }
                repository.calculate(investmentInfo)
            }.onSuccess { result ->
                _state.update { it.copy(isLoading = false, data = result) }
            }.onFailure { error ->
                _state.update {
                    it.copy(isLoading = false, error = UiText.Dynamic(error.message.toString()))
                }
            }
        }
    }
}