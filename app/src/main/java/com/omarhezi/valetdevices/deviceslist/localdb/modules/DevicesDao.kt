package com.omarhezi.valetdevices.deviceslist.localdb.modules

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DevicesDao {
    @Query("SELECT * FROM favorite_devices_table")
    fun getFavoriteDevices(): Flow<List<DeviceEntity>>

    @Insert
    fun addFavoriteDevice(device: DeviceEntity)

    @Query("DELETE FROM favorite_devices_table WHERE id = :deviceId")
    fun removeFavoriteDevice(deviceId: String)
}