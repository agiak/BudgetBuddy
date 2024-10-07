package com.example.common.myutils

import android.app.DatePickerDialog
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.common.APP_DATE_FORMAT
import com.agcoding.common.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun EditText.setCursorPositionToEnd() = setSelection(text.toString().length)

fun EditText.onDateListener(format: String = APP_DATE_FORMAT) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    setOnClickListener {
        DatePickerDialog(context, R.style.TimePickerTheme,
            { _, year, month, day ->
                this.setText(getFormattedTime(year, month, day, format))
                hideKeyboard()
            }, year, month, day
        ).show()
    }
}

// format time from date picker in birthdate field
private fun getFormattedTime(year: Int, month: Int, dayOfMonth: Int, format: String): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth)
    val dateFormatter = SimpleDateFormat(format, Locale.ENGLISH)
    return dateFormatter.format(calendar.time)
}

fun EditText.addCharAtTheEnd(symbol: String) {
    doOnTextChanged { text, _, _, _ ->
        val currentText = text.toString()

        when {
            currentText.isEmpty() -> setText("")
            !currentText.endsWith(symbol) -> {
                // Remove the symbol if already exists to prevent duplication
                val newText = currentText.removeSuffix(symbol) + symbol

                this.setText(newText)

                // Move the cursor before the symbol
                this.setSelection(newText.length - 1)
            }
        }
    }
}