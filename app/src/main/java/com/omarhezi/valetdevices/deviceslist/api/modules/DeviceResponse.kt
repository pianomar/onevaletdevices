package com.omarhezi.valetdevices.deviceslist.api.modules

import com.squareup.moshi.Json

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
	val id: String? = null,

	@Json(name="imageUrl")
	val imageUrl: String? = null
)
