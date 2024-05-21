package com.example.mywallet.features.a1.domain

import java.util.Calendar

fun main() {
    val maxYear = 10
    val currentText = "27 June 2024"

    println("Result for maxYear: $maxYear currentText: $currentText result ${isMaxYearInvalid(maxYear.toString(), currentText)}")
}

private const val YEARS = 4

fun isMaxYearInvalid(maxYear: String, currentText: String): Boolean =
    try {
        val maxYearInt = maxYear.toInt()
        val year = currentText.takeLast(YEARS).toInt()
        val currentYear = Calendar.getInstance()[Calendar.YEAR]
        currentYear - year > maxYearInt
    } catch (ex: NumberFormatException) {
        println("ex message ${ex.message}")
        false
    }