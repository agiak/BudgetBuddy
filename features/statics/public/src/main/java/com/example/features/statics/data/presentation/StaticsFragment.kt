package com.example.features.statics.data.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.hide
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showToast
import com.example.core.presentation.ext.addSpaceDecorator
import com.example.core.presentation.ext.launchWhenResumed
import com.agcoding.features.statics.databinding.FragmentStaticsBinding
import com.example.features.statics.impl.data.data.StaticsItem
import com.example.features.statics.impl.data.data.StaticsUiState
import com.example.features.statics.impl.data.presentation.StaticsAdapter
import com.example.features.statics.impl.data.presentation.viewholders.EmptyStatViewHolder
import com.example.features.statics.impl.data.presentation.viewholders.StaticsItemViewHolderFactory
import com.example.features.statics.impl.data.presentation.viewholders.charts.InvestmentProgressViewHolder
import com.example.features.statics.impl.data.presentation.viewholders.common.CommonStatsViewHolder
import com.agcoding.features.statics.impl.databinding.ItemEmptyStatsBinding
import com.agcoding.features.statics.impl.databinding.ItemStaticsCommonStatsBinding
import com.agcoding.features.statics.impl.databinding.ItemStaticsInvestmentProgressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class StaticsFragment : Fragment() {

    private val viewModel: StaticsViewModel by viewModels()

    private var _binding: FragmentStaticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var statsAdapter: StaticsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStaticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                Timber.d("state $state")
                binding.loader.hide()
                when (state) {
                    is StaticsUiState.Error -> showToast(state.errorMessage)
                    StaticsUiState.Idle -> {}
                    StaticsUiState.Loading -> binding.loader.show()
                    is StaticsUiState.Result -> {
                        statsAdapter.submitList(state.statsItems)
                    }
                }
            }
        }
    }

    private fun initViews() {
        statsAdapter = StaticsAdapter(createViewHolders())
        binding.statsList.apply {
            adapter = statsAdapter
            addSpaceDecorator(16)
        }
    }

    private fun createViewHolders(): List<StaticsItemViewHolderFactory<*>> =
        listOf(
            StaticsItemViewHolderFactory<StaticsItem.CommonStats> { parent ->
                val binding = ItemStaticsCommonStatsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CommonStatsViewHolder(binding = binding)
            },
            StaticsItemViewHolderFactory<StaticsItem.InvestmentProgress> { parent ->
                val binding = ItemStaticsInvestmentProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                InvestmentProgressViewHolder(binding = binding)
            },
            StaticsItemViewHolderFactory<StaticsItem.EmptyStats> { parent ->
                val binding = ItemEmptyStatsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EmptyStatViewHolder(binding = binding)
            }
        )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}