package com.omarhezi.valetdevices.deviceslist.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.omarhezi.valetdevices.deviceslist.api.DevicesAPI
import com.omarhezi.valetdevices.deviceslist.api.DevicesListRepository
import com.omarhezi.valetdevices.deviceslist.api.RequestResult
import com.omarhezi.valetdevices.deviceslist.api.modules.DeviceResponse
import com.omarhezi.valetdevices.deviceslist.api.modules.DevicesListResponse
import com.omarhezi.valetdevices.deviceslist.localdb.FavoriteDevicesRepository
import com.omarhezi.valetdevices.deviceslist.ui.SectionItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class DevicesListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val api: DevicesAPI = mockk()
    private val repository = mockk<DevicesListRepository>()
    private val favoritesRepository = mockk<FavoriteDevicesRepository>()
    private val viewModel = DevicesListViewModel(repository, favoritesRepository)

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testGetAllDevices() {

        coEvery { favoritesRepository.getFavoriteDevices() } returns flowOf(listOf())
        coEvery { repository.getAllDevices() } returns RequestResult.Success(listOf(
            Device(
                id = ID
            )
        ))
        coEvery { api.getAllDevices() } returns DevicesListResponse(
            devices = listOf(
                DeviceResponse(id = ID)
            )
        )

        viewModel.getAllDevices()

        assert(viewModel.devicesLiveData.getOrAwaitValue().first() is SectionItem.Header)
        assert(
            viewModel.devicesLiveData.getOrAwaitValue()
                .find { it is SectionItem.DeviceViewData && it.id == ID } != null)

        coVerify { repository.getAllDevices() }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    companion object {
        const val ID = "1234"
    }
}