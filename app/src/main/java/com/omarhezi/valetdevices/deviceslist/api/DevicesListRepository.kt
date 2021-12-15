package com.omarhezi.valetdevices.deviceslist.api

import com.omarhezi.valetdevices.deviceslist.core.toDevice
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DevicesListRepository(
    private val devicesAPI: DevicesAPI,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getAllDevices() = withContext(dispatcher) {
        try {
            val devices = devicesAPI.getAllDevices()
            val devicesList = devices.devices
            RequestResult.Success(devicesList?.map {
                it.toDevice()
            }.orEmpty())
        } catch (throwable: Throwable) {
            RequestResult.Error
        }
    }
}