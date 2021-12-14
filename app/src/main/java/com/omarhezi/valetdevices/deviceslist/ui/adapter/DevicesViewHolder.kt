package com.omarhezi.valetdevices.deviceslist.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.omarhezi.valetdevices.databinding.DeviceListitemBinding
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem

class DevicesViewHolder(
    private val binding: DeviceListitemBinding,
    private val favoriteListener: FavoriteDeviceListener? = null,
    private val deviceListener: DeviceListener? = null
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewData: SectionItem.DeviceViewData) {
        val context = binding.root.context

        binding.deviceName.text = viewData.title
        viewData.status?.let { binding.deviceStatus.text = context.getString(it) }
        binding.deviceStatus.setTextColor(context.getColor(viewData.statusColor))
        binding.deviceType.text = viewData.type
        binding.favButton.isChecked = viewData.isFavorite ?: false

        Glide
            .with(context)
            .load(viewData.imageUrl)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
            .into(binding.deviceImage)

        binding.favButton.setOnClickListener {
            viewData.id?.let {
                favoriteListener?.onFavoriteSelected(it, binding.favButton.isChecked)
            }
        }

        binding.root.setOnClickListener {
            viewData.id?.let {
                deviceListener?.onDeviceSelected(it)
            }
        }
    }

    fun interface FavoriteDeviceListener {
        fun onFavoriteSelected(deviceId: String, favorite: Boolean)
    }

    fun interface DeviceListener {
        fun onDeviceSelected(deviceId: String)
    }
}