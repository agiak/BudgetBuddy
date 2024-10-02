package com.example.core.domain.ext

fun String.toCurrencyDouble(): Double? {
    val cleanedString = this.replace("[^0-9.]".toRegex(), "")
    return cleanedString.toDoubleOrNull()
}