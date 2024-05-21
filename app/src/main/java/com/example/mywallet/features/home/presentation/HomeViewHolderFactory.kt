package com.example.mywallet.features.home.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.features.home.data.HomeItem

fun interface HomeViewHolderFactory<T : HomeItem> {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}