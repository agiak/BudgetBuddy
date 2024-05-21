package com.example.mywallet.features.home.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.features.home.data.HomeItem
import com.example.mywallet.features.home.presentation.accounts.AccountsViewHolder
import com.example.mywallet.features.home.presentation.accounts.AddAccountViewHolder
import com.example.mywallet.features.home.presentation.activity.ActivityViewHolder
import com.example.mywallet.features.home.presentation.summary.SummaryViewHolder
import com.example.mywallet.features.home.presentation.transfer.TransferFundsViewHolder

class HomeAdapter(
    private val viewHolderFactories: List<HomeViewHolderFactory<out HomeItem>>
) : ListAdapter<HomeItem, RecyclerView.ViewHolder>(HomeItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SUMMARY = 1
        private const val VIEW_TRANSFER_FUNDS = 2
        private const val VIEW_TYPE_ACTIVITY = 3
        private const val VIEW_TYPE_ACCOUNTS = 4
        private const val VIEW_TYPE_ADD_ACCOUNT = 5
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is HomeItem.Summary -> VIEW_TYPE_SUMMARY
            is HomeItem.Activity -> VIEW_TYPE_ACTIVITY
            is HomeItem.Accounts -> VIEW_TYPE_ACCOUNTS
            HomeItem.TransferFunds -> VIEW_TRANSFER_FUNDS
            HomeItem.AddAccount -> VIEW_TYPE_ADD_ACCOUNT
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SUMMARY -> (viewHolderFactories[0] as HomeViewHolderFactory<*>).create(parent)
            VIEW_TRANSFER_FUNDS -> (viewHolderFactories[1] as HomeViewHolderFactory<*>).create(
                parent
            )

            VIEW_TYPE_ACTIVITY -> (viewHolderFactories[2] as HomeViewHolderFactory<*>).create(parent)
            VIEW_TYPE_ACCOUNTS -> (viewHolderFactories[3] as HomeViewHolderFactory<*>).create(parent)
            VIEW_TYPE_ADD_ACCOUNT -> (viewHolderFactories[4] as HomeViewHolderFactory<*>).create(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeItem.Summary -> (holder as SummaryViewHolder).bind(item)
            is HomeItem.TransferFunds -> (holder as TransferFundsViewHolder).bind()
            is HomeItem.Activity -> (holder as ActivityViewHolder).bind(item)
            is HomeItem.Accounts -> (holder as AccountsViewHolder).bind(item)
            is HomeItem.AddAccount -> (holder as AddAccountViewHolder).bind()
        }
    }

    private class HomeItemDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }
    }
}
