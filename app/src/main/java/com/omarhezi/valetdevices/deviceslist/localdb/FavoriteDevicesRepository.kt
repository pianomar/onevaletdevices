package com.omarhezi.valetdevices.deviceslist.localdb

import com.omarhezi.valetdevices.deviceslist.core.Device
import com.omarhezi.valetdevices.deviceslist.core.toDevice
import com.omarhezi.valetdevices.deviceslist.core.toDeviceEntity
import com.omarhezi.valetdevices.deviceslist.localdb.modules.DevicesDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoriteDevicesRepository(
    private val dao: DevicesDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getFavoriteDevices() = withContext(dispatcher) {
        val favoriteDevices = dao.getFavoriteDevices()
        favoriteDevices.map { deviceEntities ->
            deviceEntities.map {
                it.toDevice()
            }
        }
    }

    suspend fun favoriteDevice(device: Device) = withContext(dispatcher) {
        dao.addFavoriteDevice(device.toDeviceEntity())
    }

    suspend fun removeFavoriteDevice(device: Device) = withContext(dispatcher) {
        device.id?.let { dao.removeFavoriteDevice(it) }
    }
}