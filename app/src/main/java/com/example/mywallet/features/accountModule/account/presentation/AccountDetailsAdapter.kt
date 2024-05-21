package com.example.mywallet.features.accountModule.account.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.features.accountModule.account.data.AccountDetailsItem
import com.example.mywallet.features.accountModule.account.presentation.viewholders.AccountDetailsViewHolderFactory
import com.example.mywallet.features.accountModule.account.presentation.viewholders.activity.AccountActivityViewHolder
import com.example.mywallet.features.accountModule.account.presentation.viewholders.statics.AccountStaticsViewHolder

class AccountDetailsAdapter(
    private val viewHolderFactories: List<AccountDetailsViewHolderFactory<out AccountDetailsItem>>
) : ListAdapter<AccountDetailsItem, RecyclerView.ViewHolder>(AccountDetailsItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_STATICS = 0
        private const val VIEW_TYPE_ACTIVITY = 1
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is AccountDetailsItem.Statics -> VIEW_TYPE_STATICS
            is AccountDetailsItem.Activity -> VIEW_TYPE_ACTIVITY
            else -> 0
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_STATICS -> (viewHolderFactories[0] as AccountDetailsViewHolderFactory<*>).create(parent)
            VIEW_TYPE_ACTIVITY -> (viewHolderFactories[1] as AccountDetailsViewHolderFactory<*>).create(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is AccountDetailsItem.Statics -> (holder as AccountStaticsViewHolder).bind(item)
            is AccountDetailsItem.Activity -> (holder as AccountActivityViewHolder).bind(item)
            else -> {}
        }
    }

    private class AccountDetailsItemDiffCallback : DiffUtil.ItemCallback<AccountDetailsItem>() {
        override fun areItemsTheSame(oldItem: AccountDetailsItem, newItem: AccountDetailsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AccountDetailsItem, newItem: AccountDetailsItem): Boolean {
            return oldItem == newItem
        }
    }
}
