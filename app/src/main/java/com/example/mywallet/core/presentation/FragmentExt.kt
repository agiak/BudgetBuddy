package com.example.mywallet.core.presentation

import android.content.Context
import android.content.Intent
import com.example.mywallet.core.presentation.authflow.AuthActivity
import com.example.mywallet.core.presentation.mainflow.MainActivity

fun Context.openMainFlow() {
    startActivity(Intent(this, MainActivity::class.java))
}

fun Context.startAuthFlow() {
    startActivity(Intent(this, AuthActivity::class.java))
}