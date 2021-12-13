package com.omarhezi.valetdevices.deviceslist.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.deviceslist.api.DevicesListRepository
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesListViewModel @Inject constructor(
    private val repository: DevicesListRepository
) : ViewModel() {

    private val _devicesLiveData = MutableLiveData<List<SectionItem>>()
    val devicesLiveData: LiveData<List<SectionItem>> = _devicesLiveData

    private var devicesSections = listOf<SectionItem>()

    fun getAllDevices() {
        viewModelScope.launch {
            val allDevices = repository.getAllDevices().map {
                it.toViewData()
            }

            val sections = generateSections(allDevices)

            devicesSections = sections
            _devicesLiveData.value = sections
        }
    }

    fun getDevicesByQuery(text: String) {
        if (text.trim().isEmpty()) { // reset
            _devicesLiveData.value = devicesSections
        } else {
            val filteredList = devicesSections.filter {
                (it as? SectionItem.DeviceViewData)?.title?.contains(text, ignoreCase = true) ?: false
            }

            _devicesLiveData.value = generateSections(filteredList)
        }
    }

    private fun generateSections(allDevices: List<SectionItem>): MutableList<SectionItem> {
        val sections = mutableListOf<SectionItem>(SectionItem.Header(R.string.all_devices))
        sections.addAll(allDevices)
        return sections
    }
}