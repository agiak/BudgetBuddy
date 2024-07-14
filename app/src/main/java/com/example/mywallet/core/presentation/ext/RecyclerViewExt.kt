package com.example.mywallet.core.presentation.ext

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.core.presentation.decorators.SpaceDecoration

fun RecyclerView.addDividerDecorator() {
    val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val dividerDrawable = ContextCompat.getDrawable(context, com.example.common.R.color.primary_light)
    dividerDrawable?.let { itemDecoration.setDrawable(it) }
    addItemDecoration(itemDecoration)
}

fun RecyclerView.addSpaceDecorator(spaceInDp: Int) {
    val spaceItemDecoration =
        SpaceDecoration(spaceInDp.dpToPx(context)) // You can specify the space in pixels
    addItemDecoration(spaceItemDecoration)
}