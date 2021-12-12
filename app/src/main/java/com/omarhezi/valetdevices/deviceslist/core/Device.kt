package com.omarhezi.valetdevices.deviceslist.core

import com.omarhezi.valetdevices.deviceslist.api.modules.DeviceResponse
import com.squareup.moshi.Json

data class Device(
    val currency: String? = null,
    val price: Int? = null,
    val type: String? = null,
    val isFavorite: Boolean? = null,
    val description: String? = null,
    val title: String? = null,
    val id: String? = null,
    val imageUrl: String? = null
)

fun DeviceResponse.toDevice() = Device(
    currency, price, type, isFavorite, description, title, id, imageUrl
)