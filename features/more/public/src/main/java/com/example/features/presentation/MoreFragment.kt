package com.example.features.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.common.myutils.disableFullScreenTheme
import com.example.common.myutils.setLightStatusBars
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.features.impl.data.MoreItem
import com.example.features.impl.data.MoreItem.InvestmentCalculator
import com.example.features.impl.data.MoreItem.Rule
import com.example.features.impl.data.MoreItem.StockMarket
import com.example.features.impl.data.MoreItem.entries
import com.agcoding.features.impl.databinding.FragmentMoreBinding
import com.example.features.impl.presentation.MoreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    private val moreAdapter =
        MoreAdapter(onClick = { selectedItem ->
            handleSelection(selectedItem)
        })

    private fun handleSelection(selectedItem: MoreItem) {
        when(selectedItem) {
            Rule -> navigateToRulesScreen()
            StockMarket -> {}
            InvestmentCalculator -> navigateToCalculatorScreen()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoreBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableFullScreenTheme()
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            moreAdapter.submitList(entries)
        }
    }

    private fun initViews() {
        binding.moreList.apply {
            adapter = moreAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun MoreFragment.navigateToCalculatorScreen() =
    navigateToNextScreen(MoreFragmentDirections.actionNavigationMoreToNavigationCalculator())

private fun MoreFragment.navigateToRulesScreen() =
    navigateToNextScreen(MoreFragmentDirections.actionNavigationMoreToGraphRules())