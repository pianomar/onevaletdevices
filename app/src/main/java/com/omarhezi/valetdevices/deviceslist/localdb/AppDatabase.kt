package com.omarhezi.valetdevices.deviceslist.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.omarhezi.valetdevices.deviceslist.localdb.modules.DeviceEntity
import com.omarhezi.valetdevices.deviceslist.localdb.modules.DevicesDao

@Database(entities = [DeviceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun devicesDao(): DevicesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "devices_database"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}