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
import com.example.features.calculator.impl.data.InvestmentYearProgress
import com.example.features.calculator.impl.databinding.ItemInvestmentGrowthResultBinding

class InvestmentGrowthResultAdapter :
    ListAdapter<InvestmentYearProgress, InvestmentGrowthResultAdapter.ProfileSettingViewHolder>(
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

        fun bind(investmentYearProgress: InvestmentYearProgress) {
            binding.root.text =
                formatInvestmentGrowth(investmentYearProgress.year, investmentYearProgress.amount.toCurrencyBalance())
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

    private class InvestmentResultDiffCallback : DiffUtil.ItemCallback<InvestmentYearProgress>() {
        override fun areItemsTheSame(
            oldItem: InvestmentYearProgress,
            newItem: InvestmentYearProgress
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: InvestmentYearProgress,
            newItem: InvestmentYearProgress
        ): Boolean {
            return oldItem == newItem
        }
    }
}