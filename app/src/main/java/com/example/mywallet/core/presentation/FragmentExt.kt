package com.example.mywallet.core.presentation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.mywallet.core.presentation.mainflow.MainActivity

fun Fragment.isMainFlow(): Boolean = requireActivity() is MainActivity

fun Context.startMainFlow() {
    startActivity(Intent(this, MainActivity::class.java))
}