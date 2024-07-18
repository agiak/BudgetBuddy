package com.example.features.statics.data.presentation.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.data.data.StaticsItem

fun interface StaticsItemViewHolderFactory<T: StaticsItem>  {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}