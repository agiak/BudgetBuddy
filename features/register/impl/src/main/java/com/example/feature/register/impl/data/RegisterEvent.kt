package com.example.feature.register.impl.data

sealed class RegisterEvent {
    data object Submit : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class IconChanged(val icon: String) : RegisterEvent()
    data class FirstNameChanged(val firstName: String) : RegisterEvent()
    data class LastNameChanged(val lastName: String) : RegisterEvent()
}
