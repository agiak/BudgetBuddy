package com.example.features.statics.impl.data.presentation.viewholders.charts

import androidx.recyclerview.widget.RecyclerView
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.databinding.ItemStaticsInvestmentProgressBinding
import com.patrykandpatrick.vico.core.cartesian.axis.BaseAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore

class InvestmentProgressViewHolder(
    private val binding: ItemStaticsInvestmentProgressBinding,
) : RecyclerView.ViewHolder(binding.root) {

    suspend fun bind(investmentProgress: StaticsItem.InvestmentProgress) {
        val modelProducer = CartesianChartModelProducer()
        binding.chartView.modelProducer = modelProducer

        val labelListKey = ExtraStore.Key<List<String>>()

        (binding.chartView.chart?.bottomAxis as BaseAxis).valueFormatter =
            CartesianValueFormatter { x, chartValues, _ -> chartValues.model.extraStore[labelListKey][x.toInt()] }

        modelProducer.runTransaction {
            columnSeries { series(investmentProgress.progress.values) }
            extras { it[labelListKey] = investmentProgress.progress.keys.toList() }
        }
    }
}
