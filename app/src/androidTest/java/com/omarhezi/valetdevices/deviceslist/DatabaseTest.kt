package com.omarhezi.valetdevices.deviceslist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.omarhezi.valetdevices.deviceslist.localdb.AppDatabase
import com.omarhezi.valetdevices.deviceslist.localdb.modules.DeviceEntity
import com.omarhezi.valetdevices.deviceslist.localdb.modules.DevicesDao
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var devicesDao: DevicesDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        devicesDao = database.devicesDao()
    }

    @Test
    fun testFavoriteDevice() = runBlocking {
        val deviceEntity = DeviceEntity(id = ID)
        devicesDao.addFavoriteDevice(deviceEntity)
        val firstItem = devicesDao.getFavoriteDevices().take(1).toList().flatten()
        assert(firstItem.first().id == ID)
    }

    @Test
    fun testRemoveFavoriteDevice() = runBlocking {
        val deviceEntity = DeviceEntity(id = ID)
        devicesDao.addFavoriteDevice(deviceEntity)
        devicesDao.removeFavoriteDevice(ID)
        val devices = devicesDao.getFavoriteDevices().take(1).toList().flatten()
        assert(devices.isEmpty())
    }

    @After
    fun tearDown() {
        database.close()
    }

    companion object {
        const val ID = "1234"
    }
}