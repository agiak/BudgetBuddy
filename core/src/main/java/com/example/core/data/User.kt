package com.example.core.data

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val icon: String? = null
) {
    val fullName: String = "$lastName $firstName"
}
