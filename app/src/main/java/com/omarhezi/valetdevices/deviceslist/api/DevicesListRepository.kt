package com.omarhezi.valetdevices.deviceslist.api

import com.omarhezi.valetdevices.deviceslist.core.toDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DevicesListRepository(private val devicesAPI: DevicesAPI) {
    suspend fun getAllDevices() = withContext(Dispatchers.IO) {
        val devices = devicesAPI.getAllDevices()
        val devicesList = devices.devices
        devicesList?.map {
            it.toDevice()
        }.orEmpty()
    }
}