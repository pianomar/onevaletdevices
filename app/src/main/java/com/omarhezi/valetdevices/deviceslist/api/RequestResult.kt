package com.omarhezi.valetdevices.deviceslist.api

import com.omarhezi.valetdevices.deviceslist.core.Device

sealed class RequestResult {
    data class Success(val data: List<Device>) : RequestResult()
    object Error : RequestResult()
}