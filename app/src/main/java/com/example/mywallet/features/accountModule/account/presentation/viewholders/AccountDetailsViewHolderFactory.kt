package com.example.mywallet.features.accountModule.account.presentation.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.features.accountModule.account.data.AccountDetailsItem

fun interface AccountDetailsViewHolderFactory<T : AccountDetailsItem> {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}