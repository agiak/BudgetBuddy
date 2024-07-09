package com.example.mywallet.features.account.accountDetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.common.myutils.enableFullScreenTheme
import com.example.common.myutils.hide
import com.example.common.myutils.setLightStatusBars
import com.example.common.myutils.show
import com.example.common.myutils.showToast
import com.example.mywallet.R
import com.example.mywallet.core.presentation.ext.launchWhenResumed
import com.example.mywallet.databinding.FragmentAccountBinding
import com.example.mywallet.features.account.accountDetails.data.AccountDetails
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val args: AccountFragmentArgs by navArgs()

    private val viewModel: AccountViewModel by hiltNavGraphViewModels(R.id.graph_account)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init screen
        enableFullScreenTheme()
        setLightStatusBars(true)

        // init VM
        viewModel.accountID = args.accountId
        viewModel.loadState()

        // rest of the screen functionality
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {

        launchWhenResumed {
            viewModel.account.collectLatest { accountInfo ->
                accountInfo?.let { handleGeneralInformation(it) }
            }
        }

        launchWhenResumed {
            viewModel.state.collectLatest { state ->
                hideLoading()
                when(state) {
                    AccountUiState.AccountDeleted -> findNavController().navigateUp()
                    is AccountUiState.Error -> showToast(state.errorMessage)
                    AccountUiState.Loading -> showLoading()
                    else -> {}
                }
            }
        }
    }

    private fun showLoading() {
        binding.loader.show()
    }

    private fun hideLoading() {
        binding.loader.hide()
    }

    private fun handleGeneralInformation(generalInfo: AccountDetails?) {
        generalInfo?.let {
            binding.logo.setImageResource(it.bank.drawableID)
            binding.collapsingLayout.title = it.name
        }
    }

    private fun initViews() {
        setUpTabLayout()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpTabLayout() {
        binding.viewPager.adapter = AccountViewPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.account_tab_general_info)
                1 -> tab.text = getString(R.string.account_tab_activity)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
