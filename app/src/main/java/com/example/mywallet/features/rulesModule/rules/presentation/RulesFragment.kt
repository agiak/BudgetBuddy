package com.example.mywallet.features.rulesModule.rules.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.hide
import com.example.common.myutils.setLightStatusBars
import com.example.mywallet.R
import com.example.core.presentation.ext.addDividerDecorator
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.mywallet.databinding.FragmentRulesBinding
import com.example.core.data.rule.Rule
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RulesFragment : Fragment() {

    private val viewModel: RulesViewModel by viewModels()

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    private val rulesAdapter = RulesAdapter(onClick = { navigateToSelectedRule(it) })

    private fun navigateToSelectedRule(rule: Rule) {
        when(rule) {
            Rule.Salary -> navigateToSalaryRule()
            Rule.Interest -> {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.screenTitle.text = getString(R.string.rule_screen_title)
        binding.toolbar.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.toolbar.optionsButton.hide()
    }

    private fun initSubscriptions() {
        launchWhenResumed {}
    }

    private fun initViews() {
        binding.rulesList.apply {
            adapter = rulesAdapter
            addDividerDecorator()
            rulesAdapter.submitList(Rule.entries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun RulesFragment.navigateToSalaryRule() =
    navigateToNextScreen(RulesFragmentDirections.actionNavigationRulesToNavigationRuleSalary())