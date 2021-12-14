package com.omarhezi.valetdevices.deviceslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omarhezi.valetdevices.databinding.DeviceListitemBinding
import com.omarhezi.valetdevices.databinding.DevicesListHeaderListitemBinding
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem

class DevicesListAdapter(
    private val favoriteListener: DevicesViewHolder.FavoriteDeviceListener? = null,
    private val deviceListener: DevicesViewHolder.DeviceListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<SectionItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SectionItem.ItemType.HEADER.value -> HeaderViewHolder(
                DevicesListHeaderListitemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            SectionItem.ItemType.DEVICE.value -> DevicesViewHolder(
                DeviceListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                favoriteListener,
                deviceListener
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewData = items[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(viewData as SectionItem.Header)
            is DevicesViewHolder -> holder.bind(viewData as SectionItem.DeviceViewData)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].itemType.value
}