package com.example.mywallet.features.account.accountDetails.presentation.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.features.account.accountDetails.data.AccountDetailsItem

fun interface AccountDetailsViewHolderFactory<T : AccountDetailsItem> {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}