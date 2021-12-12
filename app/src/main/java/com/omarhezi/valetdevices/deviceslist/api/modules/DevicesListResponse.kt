package com.omarhezi.valetdevices.deviceslist.api.modules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DevicesListResponse(
    @Json(name = "devices")
    val devices: List<DeviceResponse>? = null
)