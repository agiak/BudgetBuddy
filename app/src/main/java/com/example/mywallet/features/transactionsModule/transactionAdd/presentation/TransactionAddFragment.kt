package com.example.mywallet.features.transactionsModule.transactionAdd.presentation

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
import com.example.core.data.common.TransactionType
import com.example.core.data.common.isInternalTransaction
import com.example.mywallet.databinding.FragmentTransactionAddBinding
import com.example.mywallet.features.transactionsModule.transactionAdd.data.AccountSelection
import com.example.mywallet.features.transactionsModule.transactionAdd.data.TransactionNew
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionAddFragment : Fragment() {

    private val viewModel: TransactionAddViewModel by viewModels()

    private var _binding: FragmentTransactionAddBinding? = null
    private val binding get() = _binding!!

    private var accountFrom: AccountSelection? = null
    private var accountTo: AccountSelection? = null
    private var type: TransactionType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionAddBinding.inflate(inflater, container, false)
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
        binding.toolbar.screenTitle.text = getString(R.string.add_transaction_screen_title)
        binding.toolbar.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.toolbar.optionsButton.hide()
    }

    private fun initSubscriptions() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is AddTransactionUiState.Error -> showToast(state.message)
                    AddTransactionUiState.Idle -> {}
                    AddTransactionUiState.Loading -> {}
                    AddTransactionUiState.Success -> findNavController().popBackStack()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.accounts.collectLatest {
                binding.accountToField.setAdapter(
                    TransactionAddAccountsAdapter(
                        requireContext(),
                        it
                    )
                )
                binding.accountFromField.setAdapter(
                    TransactionAddAccountsAdapter(
                        requireContext(),
                        it
                    )
                )
            }
        }
    }

    private fun initViews() {
        binding.scrollView.addTitleElevation(binding.toolbar.root)

        binding.btnAddAccount.setOnClickListener { viewModel.createAccount(getNewTransaction()) }

        binding.accountFromField.setOnItemClickListener { parent, view, position, id ->
            accountFrom = parent.getItemAtPosition(position) as? AccountSelection?
        }

        binding.accountToField.setOnItemClickListener { parent, view, position, id ->
            accountTo = parent.getItemAtPosition(position) as? AccountSelection?
        }

        binding.typeField.apply {
            setAdapter(
                TransactionAddTypeAdapter(
                    context = requireContext(),
                    types = TransactionType.entries
                )
            )
            setOnItemClickListener { parent, view, position, id ->
                (parent.getItemAtPosition(position) as? TransactionType)?.let {
                    validateByType(it)
                    binding.typeField.setText(getString(it.description), false)
                }
            }
        }

        binding.dateField.apply {
            onDateListener()
            doAfterTextChanged { validateActionBtn() }
        }

        binding.accountFromField.doAfterTextChanged { validateActionBtn() }
        binding.accountToField.doAfterTextChanged { validateActionBtn() }
        binding.balanceField.doAfterTextChanged { validateActionBtn() }
    }

    private fun validateByType(transactionType: TransactionType?) {
        type = transactionType
        type?.let {
            when {
                it.isInternalTransaction() -> showInternalTransactionLayout()
                else -> showOneAccountLayout()
            }
        }
    }

    private fun showOneAccountLayout() {
        binding.accountToLayout.hide()
        binding.accountFromLayout.hint = "Select account"
        accountTo = null
    }

    private fun showInternalTransactionLayout() {
        binding.accountToLayout.show()
        binding.accountFromLayout.hint = "Select account from"
    }

    private fun validateActionBtn() {}

    private fun getNewTransaction(): TransactionNew =
        TransactionNew(
            date = binding.dateField.text.toString(),
            amount = binding.balanceField.text.toString().toDouble(),
            type = type ?: TransactionType.MONEY_TRANSFER,
            accountFrom = (accountFrom)?.id ?: 0L,
            accountFromName = (accountFrom)?.name ?: "",
            bankFromIcon = accountFrom!!.bank.drawableID,
            accountTo = accountTo?.id,
            accountToName = accountTo?.name,
            bankToIcon = accountTo?.bank?.drawableID,
            description = binding.descriptionField.text.toString(),
            applyTransaction = binding.checkboxApplyTransaction.isChecked
        )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}