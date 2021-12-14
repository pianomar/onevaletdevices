package com.omarhezi.valetdevices.deviceslist.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.deviceslist.api.DevicesListRepository
import com.omarhezi.valetdevices.deviceslist.localdb.FavoriteDevicesRepository
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesListViewModel @Inject constructor(
    private val repository: DevicesListRepository,
    private val favoriteDevicesRepository: FavoriteDevicesRepository
) : ViewModel() {

    private val _devicesLiveData = MutableLiveData<List<SectionItem>>()
    val devicesLiveData: LiveData<List<SectionItem>> = _devicesLiveData

    private var devicesSections = listOf<SectionItem>()
    private var allDevices = listOf<Device>()

    fun getAllDevices() =
        viewModelScope.launch {
            allDevices = repository.getAllDevices()
            devicesSections = generateSections(allDevices)

            _devicesLiveData.value = devicesSections
        }

    fun getDevicesByQuery(text: String) {
        if (text.trim().isEmpty()) { // reset
            _devicesLiveData.value = devicesSections
        } else {
            val filteredList = allDevices.filter {
                it.title?.contains(text, ignoreCase = true) ?: false
            }

            _devicesLiveData.value = generateSections(filteredList)
        }
    }

    fun favoriteDevice(deviceId: String, favorite: Boolean) {
        val device = allDevices.find { it.id == deviceId }

        device?.let {
            viewModelScope.launch {
                if (favorite) {
                    favoriteDevicesRepository.favoriteDevice(it)
                } else {
                    favoriteDevicesRepository.removeFavoriteDevice(it)
                }
            }
        }
    }

    fun getFav() {
        viewModelScope.launch {
            favoriteDevicesRepository.getFavoriteDevices().collect {

            }
        }
    }

    private fun generateSections(allDevices: List<Device>): MutableList<SectionItem> {
        val sections = mutableListOf<SectionItem>(SectionItem.Header(R.string.all_devices))
        sections.addAll(allDevices.map {
            it.toViewData()
        })
        return sections
    }
}