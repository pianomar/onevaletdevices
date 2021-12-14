package com.omarhezi.valetdevices.mydevices.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhezi.valetdevices.DeviceListHelper
import com.omarhezi.valetdevices.R
import com.omarhezi.valetdevices.deviceslist.core.Device
import com.omarhezi.valetdevices.deviceslist.core.toViewData
import com.omarhezi.valetdevices.deviceslist.localdb.FavoriteDevicesRepository
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDevicesViewModel @Inject constructor(
    private val favoriteDevicesRepository: FavoriteDevicesRepository
) : ViewModel() {

    private val _devicesLiveData = MutableLiveData<List<SectionItem>>()
    val devicesLiveData: LiveData<List<SectionItem>> = _devicesLiveData

    private var allFavoriteDevices: List<Device> = listOf()

    // There shouldn't be a way to favorite a device since on removing the favorite, the item disappears
    fun removeFavoriteDevice(deviceId: String) {
        viewModelScope.launch {
            allFavoriteDevices.find { it.id == deviceId }?.let { device ->
                favoriteDevicesRepository.removeFavoriteDevice(device)
            }
        }
    }

    fun getFavoriteDevices() {
        viewModelScope.launch {
            favoriteDevicesRepository.getFavoriteDevices().collect { devices ->
                allFavoriteDevices = devices
                _devicesLiveData.value = DeviceListHelper.generateSections(devices, R.string.favorite_devices)
            }
        }
    }
}