package com.example.features.account.accountDetails.editAccount.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.common.myutils.hideKeyboard
import com.example.core.data.account.AccountDetails
import com.example.core.data.bank.Bank
import com.example.core.data.bank.toBankSelectionList
import com.example.core.presentation.bank.BankAdapter
import com.example.core.presentation.ext.launchWhenResumed
import com.agcoding.features.account.R
import com.example.features.account.accountDetails.accountDetails.presentation.AccountUiState
import com.example.features.account.accountDetails.accountDetails.presentation.AccountViewModel
import com.example.features.account.accountDetails.impl.editAccount.data.AccountEditableInfo
import com.agcoding.features.account.databinding.DialogEditAccountBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditAccountFragment : BottomSheetDialogFragment() {

    private var _binding: DialogEditAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by navGraphViewModels<AccountViewModel>(R.id.graph_account)

    private var selectedBank: Bank? = null

    private val bankAdapter = BankAdapter(onClick = { selectedBank = it })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.agcoding.core.R.style.BottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        initViews()
        initSubscriptions()
    }

    private fun initViews() {

        binding.bankList.adapter = bankAdapter
        binding.btnSave.setOnClickListener {
            viewModel.updateAccountDetails(getUpdatedAccountInfo())
            binding.root.hideKeyboard()
        }
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.account.collectLatest { accountInfo: AccountDetails? ->
                accountInfo?.let {
                    bankAdapter.submitList(Bank.entries.toBankSelectionList(it.bank))
                    binding.nameField.setText(it.name)
                    binding.balanceField.setText(it.balance)
                }
            }
        }

        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                when (state) {
                    AccountUiState.AccountSaved -> dismiss()
                    else -> {}
                }
            }
        }
    }

    private fun getUpdatedAccountInfo() = AccountEditableInfo(
        name = binding.nameField.text.toString(),
        balance = binding.balanceField.text.toString(),
        bank = selectedBank ?: Bank.EUROBANK
    )

}
