package com.example.remote

import androidx.test.filters.SmallTest
import com.example.data.repository.RemoteDataSource
import com.example.remote.api.ApiService
import com.example.remote.mapper.WeatherNetworkDataMapper
import com.example.remote.source.RemoteDataSourceImp
import com.google.common.truth.Truth
import com.example.remote.utils.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class RemoteDataSourceImpTest {

    @MockK
    private lateinit var apiService : ApiService
    private val weatherNetworkDataMapper = WeatherNetworkDataMapper()

    private lateinit var remoteDataSource : RemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = RemoteDataSourceImp(
            apiService = apiService,
            weatherMapper = weatherNetworkDataMapper,
        )
    }

    @Test
    fun test_get_Weather_success() = runBlockingTest {

        val weatherNetwork = TestDataGenerator.generateWeather()

        // Given
        coEvery { apiService.getWeather(any()) } returns weatherNetwork

        // When
        val result = remoteDataSource.getWeather("cairo")

        // Then
        coVerify { apiService.getWeather(any()) }

        // Assertion
        val expected = weatherNetworkDataMapper.from(weatherNetwork)
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_Weather_fail() = runBlockingTest {

        // Given
        coEvery { apiService.getWeather(any()) } throws Exception()

        // When
        remoteDataSource.getWeather("cairo")

        // Then
        coVerify { apiService.getWeather(any()) }

    }
}