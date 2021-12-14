package com.omarhezi.valetdevices.deviceslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.databinding.DevicesFragmentBinding
import com.omarhezi.valetdevices.devicedetails.ui.DeviceDetailsFragment
import com.omarhezi.valetdevices.deviceslist.core.DevicesListViewModel
import com.omarhezi.valetdevices.deviceslist.ui.adapter.DevicesListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DevicesListFragment : Fragment() {

    private val viewModel: DevicesListViewModel by viewModels()
    private lateinit var binding: DevicesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.devices_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = setupAdapter()

        binding.favoriteDevicesButton.setOnClickListener {
            findNavController().navigate(R.id.action_my_devices_fragment)
        }

        viewModel.devicesLiveData.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        setupSearch()

        viewModel.getAllDevices()
    }

    private fun setupAdapter(): DevicesListAdapter {
        val adapter = DevicesListAdapter({ deviceId, favorite ->
            viewModel.favoriteDevice(deviceId, favorite)
        }, { deviceId ->
            findNavController().navigate(
                R.id.action_details_fragment,
                bundleOf(DeviceDetailsFragment.DEVICE to viewModel.findDeviceById(deviceId))
            )
        })
        binding.allDevicesList.adapter = adapter
        return adapter
    }

    private fun setupSearch() {
        binding.searchButton.setOnClickListener {
            binding.searchBox.visibility = View.VISIBLE
            binding.searchButton.visibility = View.GONE
            binding.searchBox.requestFocus()
        }

        binding.searchButton.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.searchBox.visibility = View.GONE
                binding.searchButton.visibility = View.VISIBLE
            }
        }

        binding.searchBox.addTextChangedListener {
            viewModel.getDevicesByQuery(it.toString())
        }
    }
}