package com.example.zooplsdemo.ui.ui.airpollutiondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.zooplsdemo.R
import com.example.zooplsdemo.databinding.FragmentAirPollutionDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AirPollutionDetailsFragment : Fragment() {

    private val airPollutionViewModel: AirPollutionViewModel by viewModels()

    private lateinit var binding: FragmentAirPollutionDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAirPollutionDetailsBinding.inflate(inflater, container, false).apply {
            this.viewModel = airPollutionViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observeData()
        viewLifecycleOwner.lifecycleScope.launch { airPollutionViewModel.fetchAirPollution() }
    }

    private fun observeData() {
        airPollutionViewModel.onError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), R.string.error_message, Toast.LENGTH_LONG).show()
        }
    }
}