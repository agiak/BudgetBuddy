package com.example.mywallet.features.account.accountsFilter.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.common.myutils.hideKeyboard
import com.example.mywallet.R
import com.example.core.presentation.ext.launchWhenResumed
import com.example.mywallet.databinding.DialogFilterAccountsBinding
import com.example.mywallet.features.account.accounts.presentation.AccountsViewModel
import com.example.mywallet.features.account.accountsFilter.data.AccountFilterSelection
import com.example.mywallet.features.account.accountsFilter.data.AccountsFilterGroupBy
import com.example.mywallet.features.account.accountsFilter.data.AccountsFilterOrderBy
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterAccountsFragment : BottomSheetDialogFragment() {

    private var _binding: DialogFilterAccountsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountsViewModel by hiltNavGraphViewModels<AccountsViewModel>(R.id.graph_accounts)

    private var orderBySelection: AccountsFilterOrderBy? = null
    private var groupBySelection: AccountsFilterGroupBy? = AccountsFilterGroupBy.BY_BANK

    private val orderByAdapter = FilterOrderByAdapter(onClick = { orderBySelection = it })
    private val groupByAdapter = FilterGroupByAdapter(onClick = { groupBySelection = it })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterAccountsBinding.inflate(inflater, container, false)
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

        binding.btnSave.setOnClickListener {
            viewModel.applyFilters(getSelectedFilters())
            binding.root.hideKeyboard()
            dismiss()
        }

        binding.orderByList.adapter = orderByAdapter
        binding.groupByList.adapter = groupByAdapter
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            val data = viewModel.fetchSelectedFiltersData()
            with(data.orderBySelections) {
                orderByAdapter.submitList(this)
                orderBySelection = find { it.isSelected }
            }
            with(data.groupBySelections) {
                groupByAdapter.submitList(this)
                groupBySelection = find { it.isSelected }
            }
        }
    }

    private fun getSelectedFilters(): AccountFilterSelection =
        AccountFilterSelection(groupBySelection, orderBySelection)
}
