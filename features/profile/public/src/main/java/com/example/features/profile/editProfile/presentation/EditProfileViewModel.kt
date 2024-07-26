package com.example.features.profile.editProfile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.User
import com.example.features.profile.impl.editProfile.domain.EditProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: EditProfileRepository
): ViewModel() {

    val user: StateFlow<User?> = flow {
        emit(repository.fetchUser())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    fun saveChanges(user: User) {
        repository.saveChanges(user)
    }

}