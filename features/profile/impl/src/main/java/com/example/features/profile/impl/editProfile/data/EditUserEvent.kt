package com.example.features.profile.impl.editProfile.data

sealed class EditUserEvent {
    data class FirstNameChanged(val value: String) : EditUserEvent()
    data class LastNameChanged(val value: String) : EditUserEvent()
    data class EmailChanged(val value: String) : EditUserEvent()
    data class IconChanged(val value: String) : EditUserEvent()
    data object OnSave : EditUserEvent()
}