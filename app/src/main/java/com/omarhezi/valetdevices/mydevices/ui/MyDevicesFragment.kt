package com.omarhezi.valetdevices.mydevices.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.databinding.MyDevicesFragmentBinding
import com.omarhezi.valetdevices.deviceslist.ui.adapter.DevicesListAdapter
import com.omarhezi.valetdevices.mydevices.core.MyDevicesViewModel
import com.omarhezi.valetdevices.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDevicesFragment : Fragment() {

    private lateinit var binding: MyDevicesFragmentBinding
    private val viewModel: MyDevicesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_devices_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = setupAdapter()

        viewModel.devicesLiveData.observe(viewLifecycleOwner) {
            binding.noFavsText.showIf { it.size == 1 }

            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        viewModel.getFavoriteDevices()
    }

    private fun setupAdapter(): DevicesListAdapter {

        val adapter = DevicesListAdapter({ deviceId, _ ->
            viewModel.removeFavoriteDevice(deviceId)
        })

        binding.favDevicesList.adapter = adapter
        return adapter
    }
}