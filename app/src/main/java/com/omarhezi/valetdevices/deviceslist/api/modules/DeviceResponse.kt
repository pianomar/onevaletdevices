package com.omarhezi.valetdevices.deviceslist.api.modules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceResponse(

	@Json(name="Currency")
	val currency: String? = null,

	@Json(name="Price")
	val price: Int? = null,

	@Json(name="Type")
	val type: String? = null,

	@Json(name="isFavorite")
	val isFavorite: Boolean? = null,

	@Json(name="Description")
	val description: String? = null,

	@Json(name="Title")
	val title: String? = null,

	@Json(name="Id")
	val id: String,

	@Json(name="imageUrl")
	val imageUrl: String? = null,

	@Json(name="Status")
	val status: Int? = null
)
