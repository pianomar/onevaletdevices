package com.omarhezi.valetdevices.deviceslist.api

import com.omarhezi.valetdevices.deviceslist.api.modules.DevicesListResponse
import retrofit2.http.GET

interface DevicesAPI {

    @GET("deviceslists")
    suspend fun getAllDevices() : DevicesListResponse
}