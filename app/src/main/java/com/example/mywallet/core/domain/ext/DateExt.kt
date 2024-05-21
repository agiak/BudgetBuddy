package com.example.mywallet.core.domain.ext

import com.example.common.APP_DATE_FORMAT
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun getCurrentDateFormatted(): String {
    val dateFormat = SimpleDateFormat(APP_DATE_FORMAT, Locale.getDefault())
    val currentDate = Date()
    return dateFormat.format(currentDate)
}

fun getCurrentDateTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")
    return current.format(formatter)
}
