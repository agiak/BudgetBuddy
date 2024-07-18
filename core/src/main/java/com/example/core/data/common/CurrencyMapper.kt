package com.example.core.data.common

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

//fun Double.toCurrencyBalance(): String = "$this€\n"

fun Double.toCurrencyBalance(): String {
    val symbols = DecimalFormatSymbols().apply {
        decimalSeparator = ','
        groupingSeparator = '.'
    }
    val df = DecimalFormat("#,##0.00", symbols)
    return df.format(this) + "€"
}