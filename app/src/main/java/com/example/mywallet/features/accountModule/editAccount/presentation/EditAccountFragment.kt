package com.example.mywallet.features.accountModule.editAccount.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.common.myutils.hideKeyboard
import com.example.mywallet.R
import com.example.mywallet.core.data.bank.Bank
import com.example.mywallet.core.data.bank.toBankSelectionList
import com.example.mywallet.core.presentation.bank.BankAdapter
import com.example.mywallet.core.presentation.ext.launchWhenResumed
import com.example.mywallet.databinding.DialogEditAccountBinding
import com.example.mywallet.features.accountModule.account.data.AccountDetails
import com.example.mywallet.features.accountModule.account.presentation.AccountViewModel
import com.example.mywallet.features.accountModule.editAccount.data.AccountEditableInfo
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
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
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
            dismiss()
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
    }

    private fun getUpdatedAccountInfo() = AccountEditableInfo(
        name = binding.nameField.text.toString(),
        balance = binding.balanceField.text.toString(),
        bank = selectedBank ?: Bank.EUROBANK
    )

}
