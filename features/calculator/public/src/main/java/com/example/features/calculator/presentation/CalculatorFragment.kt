package com.example.features.calculator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.addCharAtTheEnd
import com.example.common.myutils.addTitleElevation
import com.example.common.myutils.hide
import com.example.common.myutils.hideKeyboard
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showToast
import com.example.core.data.common.toCurrencyBalance
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.onBack
import com.example.features.calculator.R
import com.example.features.calculator.databinding.FragmentCalculatorBinding
import com.example.features.calculator.impl.data.InvestmentInfo
import com.example.features.calculator.impl.data.InvestmentResult
import com.example.features.calculator.impl.presentation.InvestmentGrowthResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CalculatorFragment : Fragment() {

    private val viewModel: CalculatorViewModel by viewModels()

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { InvestmentGrowthResultAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalculatorBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initToolbar()
        initViews()
        initSubscriptions()
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            backButton.setOnClickListener { onBack() }
            screenTitle.text = getString(R.string.calculator_screen_title)
            optionsButton.hide()
        }
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                binding.loader.isVisible = state.isLoading
                when {
                    state.data != null -> state.data?.let { showResults(it) }
                    state.error != null -> {
                        binding.resultsGroup.hide()
                        showToast(state.error!!.asString(requireContext()))
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.btnCalculate.setOnClickListener {
            viewModel.calculate(getInfo())
            binding.root.hideKeyboard()
        }

        binding.rateField.addCharAtTheEnd("%")

        binding.scrollView.addTitleElevation(binding.toolbar.root)
        binding.resultList.adapter = adapter
    }

    private fun getInfo() = InvestmentInfo(
        amount = binding.amountField.text.toString().toDouble(),
        duration = binding.durationField.text.toString().toInt(),
        rate = binding.rateField.text.toString().removeSuffix("%").toDouble() / 100,
        isYearEndInvest = binding.endYearCheck.isChecked
    )

    private fun showResults(results: InvestmentResult) {
        binding.resultsGroup.show()
        adapter.submitList(results.growthPerYear)
        results.overview?.let { overviewInfo ->
            binding.finalNetworth.text =
                getString(
                    com.example.features.calculator.impl.R.string.overview_networth,
                    overviewInfo.finalNetworth.toCurrencyBalance()
                )
            binding.contribution.text = getString(
                com.example.features.calculator.impl.R.string.total_contribution,
                overviewInfo.contribution.toCurrencyBalance()
            )
            binding.rateIncome.text = getString(
                com.example.features.calculator.impl.R.string.interest_income,
                overviewInfo.interestIncome.toCurrencyBalance()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}