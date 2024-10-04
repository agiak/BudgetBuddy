package com.example.budgetbuddy.features.home.presentation.accounts

import androidx.recyclerview.widget.RecyclerView
import com.budgetbuddy.databinding.ItemHomeAddAccountBinding

class AddAccountViewHolder(
    private val binding: ItemHomeAddAccountBinding,
    private val navigateToAddAccount: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.addAccountBtn.setOnClickListener { navigateToAddAccount() }
    }
}
