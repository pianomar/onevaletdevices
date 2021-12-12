package com.omarhezi.valetdevices.deviceslist.api.modules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DevicesListResponse {
    @Json(name="devices")
    val devices: List<DeviceResponse>? = null
}