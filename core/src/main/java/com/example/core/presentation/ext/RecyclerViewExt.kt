package com.example.core.presentation.ext

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.decorators.SpaceDecoration

fun RecyclerView.addDividerDecorator(color: Int = com.agcoding.common.R.color.primary_light) {
    val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val dividerDrawable = ContextCompat.getDrawable(context, color)
    dividerDrawable?.let { itemDecoration.setDrawable(it) }
    addItemDecoration(itemDecoration)
}

fun RecyclerView.addSpaceDecorator(spaceInDp: Int) {
    val spaceItemDecoration =
        SpaceDecoration(spaceInDp.dpToPx(context)) // You can specify the space in pixels
    addItemDecoration(spaceItemDecoration)
}