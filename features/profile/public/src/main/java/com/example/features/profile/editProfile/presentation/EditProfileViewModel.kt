package com.example.features.profile.editProfile.presentation

import androidx.lifecycle.ViewModel
import com.example.core.data.User
import com.example.core.domain.user.UserRepository
import com.example.features.profile.impl.editProfile.data.EditUserEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private var currentUser: User? = null

    init {
        with(userRepository.fetchUser()) {
            _user.value = this
            currentUser = this
        }
    }

    fun onEvent(event: EditUserEvent) {
        if (currentUser == null) return // something went wrong here
        when (event) {
            is EditUserEvent.EmailChanged -> currentUser = currentUser!!.copy(email = event.value)
            is EditUserEvent.FirstNameChanged -> currentUser =
                currentUser!!.copy(firstName = event.value)

            is EditUserEvent.IconChanged -> currentUser = currentUser!!.copy(icon = event.value)
            is EditUserEvent.LastNameChanged -> currentUser =
                currentUser!!.copy(lastName = event.value)

            EditUserEvent.OnSave -> {
                userRepository.updateUser(currentUser!!)
            }
        }

    }

}