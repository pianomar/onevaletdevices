package com.omarhezi.valetdevices.deviceslist.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.omarhezi.valetdevices.databinding.DeviceListitemBinding
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem

class DevicesViewHolder(private val binding: DeviceListitemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewData: SectionItem.DeviceViewData) {
        binding.deviceName.text = viewData.title
    }
}