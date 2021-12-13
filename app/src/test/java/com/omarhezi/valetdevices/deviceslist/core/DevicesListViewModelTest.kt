package com.omarhezi.valetdevices.deviceslist.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.omarhezi.valetdevices.deviceslist.api.DevicesAPI
import com.omarhezi.valetdevices.deviceslist.api.DevicesListRepository
import com.omarhezi.valetdevices.deviceslist.api.modules.DevicesListResponse
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class DevicesListViewModelTest : TestCase() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val api: DevicesAPI = mockk()
    private val repository = mockk<DevicesListRepository>()
    private val viewModel = DevicesListViewModel(repository)

    @Before
    override fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testGetAllDevices() = runBlocking {
            coEvery { repository.getAllDevices() } returns listOf()
            coEvery { api.getAllDevices() } returns DevicesListResponse()

            viewModel.getAllDevices()

            coVerify { repository.getAllDevices() }
        }

    @After
    override fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}