package com.example.features.calculator.impl.presentation

import android.content.Context
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.common.toCurrencyBalance
import com.example.features.calculator.impl.R
import com.example.features.calculator.impl.data.InvestmentResult
import com.example.features.calculator.impl.databinding.ItemInvestmentGrowthResultBinding

class InvestmentGrowthResultAdapter :
    ListAdapter<InvestmentResult, InvestmentGrowthResultAdapter.ProfileSettingViewHolder>(
        InvestmentResultDiffCallback()
    ) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileSettingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInvestmentGrowthResultBinding.inflate(inflater, parent, false)
        context = parent.context
        return ProfileSettingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileSettingViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class ProfileSettingViewHolder(private val binding: ItemInvestmentGrowthResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(investmentResult: InvestmentResult) {
            binding.root.text =
                formatInvestmentGrowth(investmentResult.year, investmentResult.amount.toCurrencyBalance())
        }

        fun formatInvestmentGrowth(year: String, amount: String): Spanned {
            val formattedString = String.format(
                context.getString(R.string.investment_growth),
                "<b>$year</b>",
                "<b>$amount</b>"
            )
            return HtmlCompat.fromHtml(formattedString, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    private class InvestmentResultDiffCallback : DiffUtil.ItemCallback<InvestmentResult>() {
        override fun areItemsTheSame(
            oldItem: InvestmentResult,
            newItem: InvestmentResult
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: InvestmentResult,
            newItem: InvestmentResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}