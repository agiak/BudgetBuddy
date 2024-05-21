package com.example.mywallet.features.rulesModule.ruleSalary.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.features.rulesModule.ruleSalary.data.SalaryAccount
import com.example.mywallet.features.rulesModule.ruleSalary.data.SalaryRuleData
import com.example.mywallet.features.rulesModule.ruleSalary.data.SalaryState
import com.example.mywallet.features.rulesModule.ruleSalary.data.toRuleDB
import com.example.mywallet.features.rulesModule.ruleSalary.domain.SalaryRepository
import com.example.mywallet.features.rulesModule.ruleSalary.domain.SalaryWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val repository: SalaryRepository,
    @ApplicationContext private val appContext: Context,
) : ViewModel() {

    private val _state = MutableStateFlow<SalaryState>(SalaryState.Loading)
    val state: StateFlow<SalaryState> = _state.asStateFlow()

    init { fetchState() }

    private fun fetchState() {
        viewModelScope.launch {
            runCatching {
                repository.fetchCurrentSalaryRule()
            }.onSuccess { salaryRule ->
                if (salaryRule == null) {
                    _state.value = SalaryState.NoRuleFound
                } else {
                    _state.value = SalaryState.CurrentSalaryRule(
                        amount = salaryRule.salary,
                        account = SalaryAccount(
                            id = salaryRule.accountID,
                            name = salaryRule.accountName
                        )
                    )
                }
            }.onFailure {
                _state.value = SalaryState.NoRuleFound
            }
        }
    }

    fun activateRule(salaryRule: SalaryRuleData) {
        viewModelScope.launch {
            emitLoading()
            runCatching {
                repository.enableRule(salaryRule.toRuleDB())
            }.onSuccess {
                SalaryWorker.schedule(appContext = appContext)
                _state.value = SalaryState.RuleActivated
            }
        }
    }

    fun disableRule(context: Context) {
        viewModelScope.launch {
            emitLoading()
            runCatching {
                repository.deleteRule()
            }.onSuccess {
                SalaryWorker.cancel(appContext)
                _state.value = SalaryState.NoRuleFound
            }
        }
    }

    private fun emitLoading() {
        _state.value = SalaryState.Loading
    }

}