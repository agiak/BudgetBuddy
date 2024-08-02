package com.example.common.myutils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDate(): Date? {
    return try {
        SimpleDateFormat("MM/dd/yy", Locale.ENGLISH).parse(this)
    }catch (e: Exception){
        Log.e("DateParsing",e.message ?: "General Exception")
        null
    }
}

fun String.formatToDateString(): String {
    val parts = split("/")
    val day = parts[0].padStart(2, '0')
    val month = parts[1].padStart(2, '0')
    val year = parts[2]
    return "$day/$month/$year"
}

fun String.isNumber(): Boolean = toIntOrNull() != null
