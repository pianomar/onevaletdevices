package com.omarhezi.valetdevices.deviceslist.ui

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.omarhezi.valetdevices.R

sealed class SectionItem {
    abstract val itemType: ItemType

    data class DeviceViewData(
        val currency: String? = null,
        val price: Int? = null,
        val type: String? = null,
        val isFavorite: Boolean? = null,
        val description: String? = null,
        val title: String? = null,
        val id: String? = null,
        val imageUrl: String? = null,
        @StringRes val status: Int? = null,
        @ColorRes val statusColor: Int = R.color.black,
    ) : SectionItem() {
        override val itemType = ItemType.DEVICE
    }

    data class Header(@StringRes val title: Int) : SectionItem() {
        override val itemType = ItemType.HEADER
    }

    enum class ItemType(val value: Int) {
        HEADER(ITEM_VIEW_TYPE_HEADER),
        DEVICE(ITEM_VIEW_TYPE_DEVICE)
    }

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_DEVICE = 1
    }
}