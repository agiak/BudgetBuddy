package com.example.features.account.accountDetails.accountDetails.presentation.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.features.account.accountDetails.impl.accountDetails.data.AccountDetailsItem

fun interface AccountDetailsViewHolderFactory<T : AccountDetailsItem> {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}