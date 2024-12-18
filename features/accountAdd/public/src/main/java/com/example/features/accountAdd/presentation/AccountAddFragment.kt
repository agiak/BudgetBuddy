package com.example.features.accountAdd.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.addTitleElevation
import com.example.common.myutils.hide
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showSnackBar
import com.example.common.myutils.showToast
import com.example.core.data.bank.Bank
import com.example.core.data.bank.toBankSelectionList
import com.example.core.data.screens.AuthorizationFlow
import com.example.core.presentation.BackPressHandler
import com.example.core.presentation.bank.BankAdapter
import com.example.core.presentation.ext.displayExitDialog
import com.example.core.presentation.ext.isMainFlow
import com.example.core.presentation.ext.onBack
import com.agcoding.features.accountAdd.R
import com.agcoding.features.accountAdd.databinding.FragmentAddAccountBinding
import com.example.features.accountAdd.impl.data.AccountNew
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountAddFragment : Fragment(), BackPressHandler {

    private val viewModel: AddAccountViewModel by viewModels()

    private var _binding: FragmentAddAccountBinding? = null
    private val binding get() = _binding!!

    private var selectedBank: Bank? = null
    private val bankAdapter = BankAdapter(onClick = { selectedBank = it })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddAccountBinding.inflate(inflater, container, false)
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
        if (isMainFlow()) {
            with(binding.toolbar) {
                root.show()
                screenTitle.text = getString(R.string.add_account_screen_title)
                backButton.setOnClickListener { onBack() }
                optionsButton.hide()
            }
            binding.description.hide()
        } else {
            binding.toolbar.root.hide()
        }
    }

    private fun initSubscriptions() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is AddAccountUiState.Error -> showToast(state.message)
                    AddAccountUiState.Idle -> {}
                    AddAccountUiState.Loading -> showLoading()
                    AddAccountUiState.Success -> onSuccess()
                }
            }
        }
    }

    private fun onSuccess() {
        when {
            isMainFlow() -> {
                showSnackBar(getString(R.string.account_added_successfully))
                onBack()
            }

            else -> (requireActivity() as? AuthorizationFlow)?.startMainFlow()
        }
    }

    private fun showLoading() {}

    private fun initViews() {
        binding.scrollView.addTitleElevation(binding.toolbar.root)
        binding.btnAddAccount.setOnClickListener { viewModel.createAccount(getAccount()) }

        binding.nameField.doAfterTextChanged { validateActionBtn() }
        binding.balanceField.doAfterTextChanged { validateActionBtn() }

        binding.bankList.adapter = bankAdapter
        bankAdapter.submitList(Bank.entries.toBankSelectionList())
    }

    private fun validateActionBtn() {
        binding.btnAddAccount.isEnabled = binding.nameField.text?.isNotEmpty() == true
                && binding.balanceField.text?.isNotEmpty() == true
    }

    private fun getAccount() = AccountNew(
        name = binding.nameField.text.toString(),
        bank = selectedBank ?: Bank.EUROBANK,
        balance = binding.balanceField.text.toString().toDouble(),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPress() {
        if (findNavController().backQueue.size <= 3 && !isMainFlow()) displayExitDialog() else onBack()
    }
}
