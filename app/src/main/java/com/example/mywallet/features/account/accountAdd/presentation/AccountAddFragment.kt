package com.example.mywallet.features.account.accountAdd.presentation

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
import com.example.common.myutils.onDateListener
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showToast
import com.example.mywallet.R
import com.example.mywallet.core.data.bank.Bank
import com.example.mywallet.core.data.bank.toBankSelectionList
import com.example.mywallet.core.presentation.bank.BankAdapter
import com.example.mywallet.core.presentation.ext.isMainFlow
import com.example.mywallet.core.presentation.ext.startMainFlow
import com.example.mywallet.databinding.FragmentAddAccountBinding
import com.example.mywallet.features.account.accountAdd.data.AccountNew
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountAddFragment : Fragment() {

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
                backButton.setOnClickListener { findNavController().navigateUp() }
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
            isMainFlow() -> findNavController().popBackStack()
            else -> requireContext().startMainFlow()
        }
    }

    private fun showLoading() {}

    private fun initViews() {
        binding.scrollView.addTitleElevation(binding.toolbar.root)
        binding.btnAddAccount.setOnClickListener { viewModel.createAccount(getAccount()) }
        binding.dateField.apply {
            onDateListener()
            doAfterTextChanged { validateActionBtn() }
        }

        binding.nameField.doAfterTextChanged { validateActionBtn() }
        binding.balanceField.doAfterTextChanged { validateActionBtn() }

        binding.bankList.adapter = bankAdapter
        bankAdapter.submitList(Bank.entries.toBankSelectionList())
    }

    private fun validateActionBtn() {
        binding.btnAddAccount.isEnabled = binding.nameField.text?.isNotEmpty() == true
                && binding.dateField.text?.isNotEmpty() == true &&
                binding.balanceField.text?.isNotEmpty() == true
    }

    private fun getAccount() = AccountNew(
        name = binding.nameField.text.toString(),
        bank = selectedBank ?: Bank.EUROBANK,
        balance = binding.balanceField.text.toString().toDouble(),
        date = binding.dateField.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
