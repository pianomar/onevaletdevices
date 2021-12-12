package com.omarhezi.valetdevices.deviceslist.api

import com.omarhezi.valetdevices.deviceslist.api.modules.DevicesListResponse
import retrofit2.http.GET

interface DevicesAPI {

    @GET("v1/all-devices")
    fun getAllDevices() : DevicesListResponse
}