package com.example.data

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.data.mapper.WeatherDataDomainMapper
import com.example.data.model.WeatherDTO
import com.example.data.repository.LocalDataSource
import com.example.data.repository.RemoteDataSource
import com.example.data.repository.RepositoryImp
import com.google.common.truth.Truth
import com.example.data.utils.TestDataGenerator
import com.example.domain.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class RepositoryImpTest {

    @MockK
    private lateinit var localDataSource: LocalDataSource
    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    private val weatherMapper = WeatherDataDomainMapper()

    private lateinit var repository : Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        repository = RepositoryImp(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            weatherMapper = weatherMapper
        )
    }

    @Test
    fun test_get_posts_remote_success() = runBlockingTest {

        val weatherItem = TestDataGenerator.generateWeather()
        val affectedIds = 1

        // Given
        coEvery { remoteDataSource.getWeather("cairo") } returns weatherItem
        coEvery { localDataSource.addItem(weatherItem) } returns affectedIds.toLong()
        coEvery { localDataSource.getItem(any()) } returns WeatherDTO(id = -1,name = "",desc = "",temp = 0.0,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)

        // When & Assertions
        val flow = repository.getWeather("cairo")
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData.id).isEqualTo(weatherMapper.from(weatherItem).id)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getWeather("cairo") }
        coVerify { localDataSource.addItem(weatherItem) }
    }

    @Test
    fun test_get_posts_remote_fail_local_success() = runBlockingTest {

        val weather = TestDataGenerator.generateWeather()

        // Given
        coEvery { remoteDataSource.getWeather("cairo") } throws Exception()
        coEvery { localDataSource.getItem(any()) } returns weather

        // When && Assertions
        val flow = repository.getWeather("cairo")
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData.id).isEqualTo(weatherMapper.from(weather).id)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getWeather("cairo") }
        coVerify { localDataSource.getItem(any()) }

    }

    @Test
    fun test_get_posts_remote_fail_local_fail() = runBlockingTest {


        // Given
        coEvery { remoteDataSource.getWeather("cairo") } throws Exception()
        coEvery { localDataSource.getItem(any()) } throws Exception()

        // When && Assertions
        val flow = repository.getWeather("cairo")
        flow.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getWeather("cairo") }
        coVerify { localDataSource.getItem(any()) }

    }

}