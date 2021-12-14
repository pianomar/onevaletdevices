package com.omarhezi.valetdevices

import androidx.annotation.StringRes
import com.omarhezi.valetdevices.deviceslist.core.Device
import com.omarhezi.valetdevices.deviceslist.core.toViewData
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem

object DeviceListHelper {
    fun generateSections(allDevices: List<Device>, @StringRes titleRes: Int): MutableList<SectionItem> {
        val sections = mutableListOf<SectionItem>(SectionItem.Header(titleRes))
        sections.addAll(allDevices.map {
            it.toViewData()
        })
        return sections
    }
}