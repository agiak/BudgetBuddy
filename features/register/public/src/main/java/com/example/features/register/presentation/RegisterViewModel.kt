package com.example.features.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.User
import com.example.core.domain.user.UserRepository
import com.example.feature.register.impl.data.RegisterEvent
import com.example.feature.register.impl.domain.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private var localUser = User()

    val hasAccount = flow<Boolean> {
        repository.hasAccounts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )

    fun register(user: User) {
        userRepository.updateUser(user)
        _user.update { user }
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> localUser = localUser.copy(email = event.email)
            is RegisterEvent.FirstNameChanged -> localUser =
                localUser.copy(firstName = event.firstName)

            is RegisterEvent.IconChanged -> localUser = localUser.copy(icon = event.icon)
            is RegisterEvent.LastNameChanged -> localUser =
                localUser.copy(lastName = event.lastName)

            RegisterEvent.Submit -> {
                _user.update { localUser }
                userRepository.updateUser(localUser)
            }
        }
    }

}
