package com.example.mywallet.features.rulesModule.rules.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mywallet.databinding.ItemRuleBinding
import com.example.core.data.rule.Rule

class RulesAdapter(
    private val onClick: (rule: Rule) -> Unit,
) : ListAdapter<Rule, RulesAdapter.AccountViewHolder>(RulesDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRuleBinding.inflate(inflater, parent, false)
        context = parent.context
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val rule = getItem(position)
        holder.bind(rule)
    }

    inner class AccountViewHolder(private val binding: ItemRuleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rule: Rule) {
            binding.title.text = context.getString(rule.title)
            binding.description.text = context.getString(rule.description)
            Glide.with(context).load(rule.iconID).circleCrop().into(binding.image)
            binding.root.setOnClickListener { onClick(rule) }
        }
    }

    private class RulesDiffCallback : DiffUtil.ItemCallback<Rule>() {
        override fun areItemsTheSame(oldItem: Rule, newItem: Rule): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Rule, newItem: Rule): Boolean {
            return oldItem == newItem
        }
    }
}
