package com.example.local

import androidx.test.filters.SmallTest
import com.example.data.repository.LocalDataSource
import com.example.local.database.WeatherDAO
import com.example.local.mapper.WeatherLocalDataMapper
import com.example.local.source.LocalDataSourceImp
import com.example.local.utils.TestData
import com.google.common.truth.Truth
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
class LocalDataSourceImpTest {

    @MockK
    private lateinit var weatherDAO: WeatherDAO

    private val weatherLocalDataMapper = WeatherLocalDataMapper()
    private lateinit var localDataSource : LocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create LocalDataSourceImp before every test
        localDataSource = LocalDataSourceImp(
            weatherDAO = weatherDAO,
            weatherMapper = weatherLocalDataMapper
        )
    }

    @Test
    fun test_add_weather_item_success() = runBlockingTest {

        val weatherLocal = TestData.generateWeatherItem()
        val expected = 1L

        // Given
        coEvery { weatherDAO.addWeatherItem(any()) } returns expected

        // When
        val returned = localDataSource.addItem(weatherLocalDataMapper.from(i = weatherLocal))

        // Then
        coVerify { weatherDAO.addWeatherItem(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_add_weather_item_fail() = runBlockingTest {

        val weatherItem = weatherLocalDataMapper.from(i = TestData.generateWeatherItem())

        // Given
        coEvery { weatherDAO.addWeatherItem(any()) } throws Exception()

        // When
        localDataSource.addItem(weatherItem)

        // Then
        coVerify { weatherDAO.addWeatherItem(any()) }

    }

    @Test
    fun test_get_weather_item_success() = runBlockingTest {

        val weatherLocal = TestData.generateWeatherItem()
        val expected = weatherLocalDataMapper.from(i = weatherLocal)

        // Given
        coEvery { weatherDAO.getWeatherItem(any()) } returns weatherLocal

        // When
        val returned = localDataSource.getItem(weatherLocal.name)

        // Then
        coVerify { weatherDAO.getWeatherItem(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_weather_item_fail() = runBlockingTest {

        val weatherItem = weatherLocalDataMapper.from(i = TestData.generateWeatherItem())

        // Given
        coEvery { weatherDAO.getWeatherItem(any()) } throws Exception()

        // When
        localDataSource.getItem(weatherItem.name)

        // Then
        coVerify { weatherDAO.getWeatherItem(any()) }

    }

    @Test
    fun test_add_weather_items_success() = runBlockingTest {

        val weatherItems = weatherLocalDataMapper.fromList(list = TestData.generateWeatherItems())
        val expected = MutableList(weatherItems.size) { index -> index.toLong() }

        // Given
        coEvery { weatherDAO.addWeatherItems(any()) } returns expected

        // When
        val returned = localDataSource.addItems(weatherItems)

        // Then
        coVerify { weatherDAO.addWeatherItems(any()) }

        // Assertion
        Truth.assertThat(returned).hasSize(expected.size)
    }

    @Test(expected = Exception::class)
    fun test_add_weather_items_fail() = runBlockingTest {

        val weatherItems = weatherLocalDataMapper.fromList(list = TestData.generateWeatherItems())

        // Given
        coEvery { weatherDAO.addWeatherItems(any()) } throws Exception()

        // When
        localDataSource.addItems(weatherItems)

        // Then
        coVerify { weatherDAO.addWeatherItems(any()) }

    }

    @Test
    fun test_get_weather_items_success() = runBlockingTest {

        val weatherItems = TestData.generateWeatherItems()
        val expected = weatherLocalDataMapper.fromList(list = weatherItems)

        // Given
        coEvery { weatherDAO.getWeatherItems() } returns weatherItems

        // When
        val returned = localDataSource.getItems()

        // Then
        coVerify { weatherDAO.getWeatherItems() }

        // Assertion
        Truth.assertThat(returned).containsExactlyElementsIn(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_weather_items_fail() = runBlockingTest {

        // Given
        coEvery { weatherDAO.getWeatherItems() } throws Exception()

        // When
        localDataSource.getItems()

        // Then
        coVerify { weatherDAO.getWeatherItems() }

    }

    @Test
    fun test_update_weather_item_success() = runBlockingTest {

        val weatherItem = weatherLocalDataMapper.from(i = TestData.generateWeatherItem())
        val expected = 1

        // Given
        coEvery { weatherDAO.updateWeatherItem(any()) } returns expected

        // When
        val returned = localDataSource.updateItem(weatherItem)

        // Then
        coVerify { weatherDAO.updateWeatherItem(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)

    }

    @Test(expected = Exception::class)
    fun test_update_weather_item_fail() = runBlockingTest {

        val weatherItem = weatherLocalDataMapper.from(i = TestData.generateWeatherItem())

        // Given
        coEvery { weatherDAO.updateWeatherItem(any()) } throws  Exception()

        // When
        localDataSource.updateItem(weatherItem)

        // Then
        coVerify { weatherDAO.updateWeatherItem(any()) }

    }

    @Test
    fun test_delete_weather_item_by_id_success() = runBlockingTest {

        val expected = 1

        // Given
        coEvery { weatherDAO.deleteWeatherItemById(any()) } returns expected

        // When
        val returned = localDataSource.deleteItemById(1)

        // Then
        coVerify { weatherDAO.deleteWeatherItemById(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)

    }

    @Test(expected = Exception::class)
    fun test_delete_weather_item_by_id_fail() = runBlockingTest {

        // Given
        coEvery { weatherDAO.deleteWeatherItemById(any()) } throws Exception()

        // When
        localDataSource.deleteItemById(1)

        // Then
        coVerify { weatherDAO.deleteWeatherItemById(any()) }

    }

    @Test
    fun test_delete_weather_item_success() = runBlockingTest {

        val weatherItem = weatherLocalDataMapper.from(i = TestData.generateWeatherItem())
        val expected = 1

        // Given
        coEvery { weatherDAO.deleteWeatherItem(any()) } returns expected

        // When
        val returned = localDataSource.deleteItem(weatherItem)

        // Then
        coVerify { weatherDAO.deleteWeatherItem(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_delete_weather_item_fail() = runBlockingTest {

        val weatherItem = weatherLocalDataMapper.from(i = TestData.generateWeatherItem())

        // Given
        coEvery { weatherDAO.deleteWeatherItem(any()) } throws Exception()

        // When
        localDataSource.deleteItem(weatherItem)

        // Then
        coVerify { weatherDAO.deleteWeatherItem(any()) }

    }

    @Test
    fun test_clear_all_weathers_success() = runBlockingTest {

        val weatherItems = weatherLocalDataMapper.fromList(list = TestData.generateWeatherItems())
        val expected = weatherItems.size // Affected row count

        // Given
        coEvery { weatherDAO.clearCachedWeatherItems() } returns expected

        // When
        val returned = localDataSource.clearCachedItems()

        // Then
        coVerify { weatherDAO.clearCachedWeatherItems() }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)

    }

    @Test(expected = Exception::class)
    fun test_clear_all_weathers_fail() = runBlockingTest {

        // Given
        coEvery { weatherDAO.clearCachedWeatherItems() } throws Exception()

        // When
        localDataSource.clearCachedItems()

        // Then
        coVerify { weatherDAO.clearCachedWeatherItems() }

    }
}