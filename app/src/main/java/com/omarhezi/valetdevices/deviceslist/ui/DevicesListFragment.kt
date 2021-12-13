package com.omarhezi.valetdevices.deviceslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.databinding.DevicesFragmentBinding
import com.omarhezi.valetdevices.deviceslist.core.DevicesListViewModel
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

        val adapter = DevicesListAdapter()
        binding.allDevicesList.adapter = adapter

        viewModel.devicesLiveData.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        viewModel.getAllDevices()
    }
}