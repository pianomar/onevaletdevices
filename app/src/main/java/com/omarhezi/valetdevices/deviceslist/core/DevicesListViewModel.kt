package com.omarhezi.valetdevices.deviceslist.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhezi.valetdevices.deviceslist.api.DevicesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesListViewModel @Inject constructor(
    private val repository: DevicesListRepository
) : ViewModel() {

    private val _devicesLiveData = MutableLiveData<List<Device>>()
    val devicesLiveData: LiveData<List<Device>> = _devicesLiveData

    fun getAllDevices() {
        viewModelScope.launch {
            val allDevices = repository.getAllDevices()
            _devicesLiveData.value = allDevices
        }
    }
}