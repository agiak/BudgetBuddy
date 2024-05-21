package com.example.mywallet.features.statics.presentation.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.features.statics.data.StaticsItem

fun interface StaticsItemViewHolderFactory<T: StaticsItem>  {
    fun create(parent: ViewGroup): RecyclerView.ViewHolder
}