package com.omarhezi.valetdevices.deviceslist.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhezi.valetdevices.DeviceListHelper
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

    private var allDevices = listOf<Device>()
    private val devicesSections
        get() = DeviceListHelper.generateSections(allDevices, R.string.all_devices)

    fun getAllDevices() =
        viewModelScope.launch {
            val favoriteDevicesFlow = favoriteDevicesRepository.getFavoriteDevices()

            // Get favorite devices and match them with the main devices list
            favoriteDevicesFlow.collect { favoriteDevices ->
                allDevices = repository.getAllDevices().onEach { device ->
                    val isFavorite = favoriteDevices.map { it.id }.contains(device.id)
                    device.isFavorite = isFavorite
                }
                _devicesLiveData.value = devicesSections
            }
        }

    fun getDevicesByQuery(text: String) {
        if (text.trim().isEmpty()) { // reset
            _devicesLiveData.value = devicesSections
        } else {
            val filteredList = allDevices.filter {
                it.title?.contains(text, ignoreCase = true) ?: false
            }

            _devicesLiveData.value = DeviceListHelper.generateSections(filteredList, R.string.all_devices)
        }
    }

    fun favoriteDevice(deviceId: String, favorite: Boolean) {
        val device = findDeviceById(deviceId)
        device?.isFavorite = favorite

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

    fun findDeviceById(deviceId: String) = allDevices.find { it.id == deviceId }
}