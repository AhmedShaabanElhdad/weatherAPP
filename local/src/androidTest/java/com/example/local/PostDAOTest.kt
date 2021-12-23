package com.example.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.local.database.AppDatabase
import com.example.local.database.WeatherDAO
import com.google.common.truth.Truth
import com.example.local.utils.TestDataGenerator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class PostDAOTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database : AppDatabase
    private lateinit var weatherDao : WeatherDAO

    @Before
    fun setUp() {
        hiltRule.inject()
        weatherDao = database.weatherDao()
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun test_add_weather_item_success() = runBlockingTest {

        val item = TestDataGenerator.generateWeatherItem()

        weatherDao.addWeatherItem(item)

        val items = weatherDao.getWeatherItems()

        Truth.assertThat(items).contains(item)

    }

    @Test
    fun test_get_weather_item_success() = runBlockingTest {

        val item = TestDataGenerator.generateWeatherItem()

        weatherDao.addWeatherItem(item)

        val result = weatherDao.getWeatherItem(item.name)

        Truth.assertThat(item).isEqualTo(result)
    }

    @Test
    fun test_add_and_get_weather_items_success() = runBlockingTest {

        val item = TestDataGenerator.generateWeatherItem()

        weatherDao.addWeatherItem(item)

        val result = weatherDao.getWeatherItems()

        Truth.assertThat(result).containsExactly(item)

    }

    @Test
    fun test_update_weather_item_success() = runBlockingTest {

        val item = TestDataGenerator.generateWeatherItem()

        weatherDao.addWeatherItem(item)

        val dbItem = weatherDao.getWeatherItem(item.name)

        Truth.assertThat(item).isEqualTo(dbItem)

        val updatedItem = item.copy(name = "updated name")

        weatherDao.updateWeatherItem(updatedItem)

        val result = weatherDao.getWeatherItem(updatedItem.name)

        Truth.assertThat(updatedItem).isEqualTo(result)

    }

    @Test
    fun test_delete_weather_item_by_id_success() = runBlockingTest {

        val item = TestDataGenerator.generateWeatherItem()

        weatherDao.addWeatherItem(item)

        val dbItem = weatherDao.getWeatherItem(item.name)

        Truth.assertThat(item).isEqualTo(dbItem)

        weatherDao.deleteWeatherItem(item)

        val items = weatherDao.getWeatherItems()

        Truth.assertThat(items).doesNotContain(item)

    }

    @Test
    fun test_delete_weather_item_success() = runBlockingTest {

        val item = TestDataGenerator.generateWeatherItem()

        weatherDao.addWeatherItem(item)

        val dbItem = weatherDao.getWeatherItem(item.name)

        Truth.assertThat(item).isEqualTo(dbItem)

        weatherDao.deleteWeatherItem(item)

        val items = weatherDao.getWeatherItems()

        Truth.assertThat(items).doesNotContain(item)

    }

    @Test
    fun test_clear_all_posts_success() = runBlockingTest {

        val items = TestDataGenerator.generateWeatherItems()

        weatherDao.addWeatherItems(items)

        val dbItems = weatherDao.getWeatherItems()

        Truth.assertThat(dbItems).containsExactlyElementsIn(items)

        weatherDao.clearCachedWeatherItems()

        val result = weatherDao.getWeatherItems()

        Truth.assertThat(result).isEmpty()
    }
}