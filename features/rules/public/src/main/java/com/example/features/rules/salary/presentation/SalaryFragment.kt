package com.example.features.rules.salary.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.addTitleElevation
import com.example.common.myutils.hide
import com.example.common.myutils.hideKeyboard
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showToast
import com.example.core.data.common.AppValues
import com.example.core.presentation.ext.isPermissionGranted
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.onBack
import com.agcoding.features.rules.R
import com.agcoding.features.rules.databinding.FragmentRuleSalaryBinding
import com.example.features.rules.salary.data.SalaryRuleData
import com.example.features.rules.salary.data.SalaryState
import com.example.features.rules.salary.data.SelectedAccount
import com.example.features.rules.salary.data.toAccountSelectedList
import com.example.features.rules.salary.data.toSelectedAccount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SalaryFragment : Fragment() {

    private val viewModel: SalaryViewModel by viewModels()

    private var _binding: FragmentRuleSalaryBinding? = null
    private val binding get() = _binding!!

    private var selectedAccount: SelectedAccount? = null
    private lateinit var accountsAdapter: SalaryAccountsAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val notificationPermission = android.Manifest.permission.POST_NOTIFICATIONS

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                activateSalaryRule()
            } else {
                showToast("Please approve this")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRuleSalaryBinding.inflate(inflater, container, false)
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
        binding.toolbar.screenTitle.text = getString(R.string.salary_screen_title)
        binding.toolbar.backButton.setOnClickListener { onBack() }
        binding.toolbar.optionsButton.hide()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                binding.loader.hide()
                when (state) {
                    is SalaryState.CurrentSalaryRule -> { onRuleFound(state) }

                    SalaryState.Loading -> onLoading()

                    SalaryState.NoRuleFound -> onNoRuleFound()
                    SalaryState.RuleActivated -> onBack()
                }
            }
        }
    }

    private fun onLoading() {
        binding.loader.show()
    }

    private fun onNoRuleFound() {

    }

    private fun onRuleFound(currentRuleState: SalaryState.CurrentSalaryRule) {
        binding.salaryDataGroup.show()
        binding.switchEnable.isChecked = true
        binding.bankField.setText(currentRuleState.account.name)
        selectedAccount = AppValues.accounts.find { it.name == currentRuleState.account.name }
            ?.toSelectedAccount() //TODO needs improvement
        binding.amountField.setText(currentRuleState.amount.toString())
    }

    private fun initViews() {
        binding.scrollView.addTitleElevation(binding.toolbar.root)

        binding.switchEnable.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.salaryDataGroup.isVisible = true
            } else {
                binding.salaryDataGroup.isVisible = false
                binding.amountField.text?.clear()
                binding.bankField.text?.clear()
                viewModel.disableRule(requireContext())
            }
        }

        accountsAdapter = SalaryAccountsAdapter(
            context = requireContext(),
            accounts = AppValues.accounts.toAccountSelectedList()
        )
        binding.bankField.apply {
            setAdapter(accountsAdapter)
            setOnItemClickListener { parent, view, position, id ->
                selectedAccount = parent.getItemAtPosition(position) as? SelectedAccount?
                hideKeyboard()
            }
        }

        binding.btnAction.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                when {
                    isPermissionGranted(notificationPermission) -> activateSalaryRule()
                    else -> notificationPermissionLauncher.launch(notificationPermission)
                }
            } else {
                activateSalaryRule()
            }
        }
    }

    private fun activateSalaryRule() {
        selectedAccount?.let {
            viewModel.activateRule(
                SalaryRuleData(
                    salary = binding.amountField.text.toString().toDouble(),
                    account = it
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
