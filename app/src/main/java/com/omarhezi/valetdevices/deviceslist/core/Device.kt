package com.omarhezi.valetdevices.deviceslist.core

import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.deviceslist.api.modules.DeviceResponse
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem

data class Device(
    val currency: String? = null,
    val price: Int? = null,
    val type: String? = null,
    val isFavorite: Boolean? = null,
    val description: String? = null,
    val title: String? = null,
    val id: String? = null,
    val imageUrl: String? = null,
    val status: Status? = null,
) {
    enum class Status(val value: Int) {
        AVAILABLE(1),
        UNAVAILABLE(0)
    }
}

fun DeviceResponse.toDevice() = Device(
    currency,
    price,
    type,
    isFavorite,
    description,
    title,
    id,
    imageUrl,
    Device.Status.values().firstOrNull { it.value == status }
)

fun Device.toViewData() = SectionItem.DeviceViewData(
    currency = currency,
    price = price,
    type = type,
    isFavorite = isFavorite,
    description = description,
    title = title,
    id = id,
    imageUrl = imageUrl,
    status = when (status) {
        Device.Status.AVAILABLE -> R.string.available
        Device.Status.UNAVAILABLE -> R.string.unavailable
        null -> R.string.unknown
    },
    statusColor = when (status) {
        Device.Status.AVAILABLE -> R.color.teal_700
        Device.Status.UNAVAILABLE -> R.color.red
        null -> R.color.black
    }
)