package com.example.features.statics.impl.data.presentation.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.impl.data.data.StaticsItem

fun interface StaticsItemViewHolderFactory<T: StaticsItem>  {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}