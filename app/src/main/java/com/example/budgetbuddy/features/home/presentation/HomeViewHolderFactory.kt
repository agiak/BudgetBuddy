package com.example.budgetbuddy.features.home.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.features.home.data.HomeItem

fun interface HomeViewHolderFactory<T : HomeItem> {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}