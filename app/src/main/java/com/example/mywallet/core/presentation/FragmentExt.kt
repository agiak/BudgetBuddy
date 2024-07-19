package com.example.mywallet.core.presentation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.core.data.screens.MainFlow
import com.example.mywallet.core.presentation.authflow.AuthActivity
import com.example.mywallet.core.presentation.mainflow.MainActivity

fun Context.openMainFlow() {
    startActivity(Intent(this, MainActivity::class.java))
}

fun Context.startAuthFlow() {
    startActivity(Intent(this, AuthActivity::class.java))
}