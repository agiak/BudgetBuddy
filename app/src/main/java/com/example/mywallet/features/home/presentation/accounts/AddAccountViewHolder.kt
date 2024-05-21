package com.example.mywallet.features.home.presentation.accounts

import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.databinding.ItemHomeAddAccountBinding

class AddAccountViewHolder(
    private val binding: ItemHomeAddAccountBinding,
    private val navigateToAddAccount: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.addAccountBtn.setOnClickListener { navigateToAddAccount() }
    }
}
