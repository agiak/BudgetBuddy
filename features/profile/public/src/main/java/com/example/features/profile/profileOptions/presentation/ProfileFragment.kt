package com.example.features.profile.profileOptions.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common.myutils.loadCircle
import com.example.common.myutils.setLightStatusBars
import com.example.core.presentation.ext.addDividerDecorator
import com.example.core.presentation.ext.launchWhenResumed
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.core.presentation.ext.onBack
import com.agcoding.features.profile.databinding.FragmentProfileBinding
import com.example.features.profile.impl.profileOptions.data.ProfileSetting
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val settingAdapter = ProfileSettingAdapter(onClick = { selectedChoice ->
        handleProfileSettingSelection(selectedChoice)
    })

    private fun handleProfileSettingSelection(selectedChoice: ProfileSetting) {
        when (selectedChoice) {
            ProfileSetting.EDIT_PROFILE -> navigateToEditProfile()
            ProfileSetting.LANGUAGE -> {}
            ProfileSetting.GUIDE -> navigateToGuide()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
        initSubscriptions()
    }

    private fun initSubscriptions() {
        launchWhenResumed {
            viewModel.user.collectLatest { user ->
                user?.let {
                    binding.profileName.text = user.fullName
                    binding.profileEmail.text = user.email
                    user.icon?.let { binding.profileImage.loadCircle(it) }
                }
            }
        }
    }

    private fun initViews() {
        binding.btnClose.setOnClickListener { onBack() }
        binding.listSettings.apply {
            adapter = settingAdapter
            addDividerDecorator()
            settingAdapter.submitList(ProfileSetting.entries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun ProfileFragment.navigateToEditProfile() =
    navigateToNextScreen(ProfileFragmentDirections.actionNavigationProfileToNavigationEditProfile())

private fun ProfileFragment.navigateToGuide() =
    navigateToNextScreen(ProfileFragmentDirections.actionNavigationProfileToNavigationGuide())
