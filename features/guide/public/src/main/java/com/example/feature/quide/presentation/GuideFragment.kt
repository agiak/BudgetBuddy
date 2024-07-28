package com.example.feature.quide.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.common.myutils.moveNext
import com.example.common.myutils.setLightStatusBars
import com.example.core.presentation.ext.isMainFlow
import com.example.core.presentation.ext.navigateToNextScreen
import com.example.core.presentation.registerScreen
import com.example.feature.quide.R
import com.example.feature.quide.databinding.FragmentGuideBinding
import com.example.features.quide.impl.data.GuideStep
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideFragment : Fragment() {

    private val viewModel: GuideViewModel by viewModels()

    private var _binding: FragmentGuideBinding? = null
    private val binding get() = _binding!!

    private val guideAdapter = GuideAdapter(steps = GuideStep.entries)
    private var viewPagerPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLightStatusBars(true)
        initViews()
    }

    private fun initViews() {
        binding.btnNext.setOnClickListener { navigateToNextStep() }

        binding.viewPager.apply {
            adapter = guideAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewPagerPosition = position
                    if (isTheLastStep()) {
                        binding.btnNext.text = getLastStepText()
                    } else {
                        binding.btnNext.text = getString(R.string.guide_button_next)
                    }
                }
            })
        }
    }

    private fun navigateToNextStep() {
        when {
            isTheLastStep() -> {
                viewModel.setGuideAsDisplayed()
                onExitGuide()
            }

            else -> binding.viewPager.moveNext()
        }
    }

    private fun onExitGuide() {
        when {
            isMainFlow() -> findNavController().navigateUp()
            else -> navigateToRegisterScreen()
        }
    }

    private fun getLastStepText(): String =
        when {
            isMainFlow() -> getString(R.string.guide_button_exit)
            else -> getString(R.string.guide_button_start)
        }

    private fun isTheLastStep() =
        viewPagerPosition == com.example.features.quide.impl.data.GuideStep.entries.size - 1

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

private fun GuideFragment.navigateToRegisterScreen() = navigateToNextScreen(registerScreen)
