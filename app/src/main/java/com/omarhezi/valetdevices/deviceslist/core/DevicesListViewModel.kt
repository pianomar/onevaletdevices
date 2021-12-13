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

    fun getAllDevices() {
        viewModelScope.launch {
            val allDevices = repository.getAllDevices().map {
                it.toViewData()
            }

            val sections = mutableListOf<SectionItem>(SectionItem.Header(R.string.all_devices))
            sections.addAll(allDevices)

            _devicesLiveData.value = sections
        }
    }
}