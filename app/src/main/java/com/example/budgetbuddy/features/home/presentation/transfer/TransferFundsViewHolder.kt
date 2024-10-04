package com.example.budgetbuddy.features.home.presentation.transfer

import androidx.recyclerview.widget.RecyclerView
import com.budgetbuddy.databinding.ItemHomeTransferFundsBinding

class TransferFundsViewHolder(
    private val binding: ItemHomeTransferFundsBinding,
    private val onTransferFundsClick: () -> Unit,
    private val onAddRulesClick: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.btnTransferFunds.setOnClickListener { onTransferFundsClick() }
        binding.btnRules.setOnClickListener { onAddRulesClick() }
    }
}
