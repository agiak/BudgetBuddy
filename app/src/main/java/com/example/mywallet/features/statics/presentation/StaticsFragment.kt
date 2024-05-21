package com.example.mywallet.features.statics.presentation

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
import com.example.mywallet.core.presentation.ext.addSpaceDecorator
import com.example.mywallet.core.presentation.ext.launchWhenResumed
import com.example.mywallet.databinding.FragmentStaticsBinding
import com.example.mywallet.databinding.ItemStaticsCommonStatsBinding
import com.example.mywallet.features.statics.data.StaticsItem
import com.example.mywallet.features.statics.data.StaticsUiState
import com.example.mywallet.features.statics.presentation.viewholders.StaticsItemViewHolderFactory
import com.example.mywallet.features.statics.presentation.viewholders.common.CommonStatsViewHolder
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
            addSpaceDecorator(32)
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
        )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}