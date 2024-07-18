package com.example.core.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
) {
    val fullName: String = "$lastName $firstName"
}
