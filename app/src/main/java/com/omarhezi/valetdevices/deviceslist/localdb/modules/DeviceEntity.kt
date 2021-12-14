package com.omarhezi.valetdevices.deviceslist.localdb.modules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "favorite_devices_table")
data class DeviceEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "Currency")
    val currency: String? = null,

    @ColumnInfo(name = "Price")
    val price: Int? = null,

    @ColumnInfo(name = "Type")
    val type: String? = null,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean? = null,

    @ColumnInfo(name = "Description")
    val description: String? = null,

    @ColumnInfo(name = "Title")
    val title: String? = null,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,

    @ColumnInfo(name = "Status")
    val status: Int? = null
)