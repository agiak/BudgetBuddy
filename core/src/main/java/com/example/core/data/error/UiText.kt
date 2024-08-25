package com.example.core.data.error

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class Dynamic(val message: String): UiText()
    data class StringResource(@StringRes val messageId: Int): UiText()

    fun asString(context: Context) =
        when(this) {
            is Dynamic -> message
            is StringResource -> context.getString(messageId)
        }
}