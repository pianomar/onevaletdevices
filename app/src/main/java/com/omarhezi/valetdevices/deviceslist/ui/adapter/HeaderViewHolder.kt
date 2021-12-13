package com.omarhezi.valetdevices.deviceslist.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.omarhezi.valetdevices.databinding.DevicesListHeaderListitemBinding
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem

class HeaderViewHolder(private val binding: DevicesListHeaderListitemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewData: SectionItem.Header) {
        binding.title.text = binding.root.context.getString(viewData.title)
    }
}