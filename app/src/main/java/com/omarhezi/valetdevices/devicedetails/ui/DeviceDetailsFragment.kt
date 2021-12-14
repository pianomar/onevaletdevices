package com.omarhezi.valetdevices.devicedetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.databinding.DeviceDetailsFragmentBinding
import com.omarhezi.valetdevices.deviceslist.core.Device
import com.omarhezi.valetdevices.deviceslist.core.toViewData
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem

class DeviceDetailsFragment : Fragment() {

    private lateinit var binding: DeviceDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.device_details_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val device = arguments?.getParcelable<Device>(DEVICE)?.toViewData()

        binding.deviceDescription.text = device?.description
        binding.devicePrice.text = "${device?.price} ${device?.currency}"
        device?.status?.let { binding.deviceStatus.text = getString(it) }
        binding.deviceType.text = device?.type
        binding.deviceNameContainer.title.text = device?.title

        Glide
            .with(this)
            .load(device?.imageUrl)
            .fitCenter()
            .into(binding.deviceImage)
    }

    companion object {
        const val DEVICE = "device"
    }
}