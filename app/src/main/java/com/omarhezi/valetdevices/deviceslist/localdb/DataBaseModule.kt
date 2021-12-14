package com.omarhezi.valetdevices.deviceslist.localdb

import android.content.Context
import com.omarhezi.valetdevices.deviceslist.localdb.modules.DevicesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesDao(database: AppDatabase) = database.devicesDao()

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providesRepository(dao: DevicesDao) = FavoriteDevicesRepository(dao)
}