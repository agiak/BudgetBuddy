package com.example.features.account.accountDetails.accountDetails.presentation.generalInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.common.myutils.showDialog
import com.example.core.data.account.AccountDetails
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.core.presentation.ext.navigateToScreen
import com.example.features.account.R
import com.example.features.account.accountDetails.accountDetails.presentation.AccountFragment
import com.example.features.account.accountDetails.accountDetails.presentation.AccountFragmentDirections
import com.example.features.account.accountDetails.accountDetails.presentation.AccountViewModel
import com.example.features.account.databinding.FragmentAccountGeneralInfoBinding
import kotlinx.coroutines.flow.collectLatest

class GeneralInfoFragment : Fragment() {

    private var _binding: FragmentAccountGeneralInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by hiltNavGraphViewModels(R.id.graph_account)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountGeneralInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.account.collectLatest { details: AccountDetails? ->
                details?.let {
                    binding.bankField.setText(it.bank.title)
                    binding.dateField.setText(it.date)
                    binding.balanceField.setText(it.balance)
                }
            }
        }
    }

    private fun initViews() {
        binding.title.setOnClickListener { navigateToEditScreen() }
        binding.btnDelete.setOnClickListener { showConfirmationDialog() }
    }

    private fun showConfirmationDialog() {
        showDialog(
            context = requireContext(),
            title = getString(R.string.account_general_info_delete_confirmation_title),
            message = getString(R.string.account_general_info_delete_confirmation_description),
            mandatoryButton = getString(R.string.account_general_info_delete_confirmation_btn_positive),
            optionalButton = getString(R.string.account_general_info_delete_confirmation_btn_negative),
            mandatoryAction = { viewModel.deleteAccount() },
            isCancelable = true
        )
    }
}

private fun GeneralInfoFragment.navigateToEditScreen() =
    findNavController().navigate(AccountFragmentDirections.actionNavigationAccountToNavigationEditAccount())