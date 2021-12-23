package com.example.domain

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.domain.repository.Repository
import com.example.domain.usecase.GetWeatherUseCase
import com.google.common.truth.Truth
import com.example.domain.utils.MainCoroutineRule
import com.example.domain.utils.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.never
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class GetWeatherUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: Repository

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        getWeatherUseCase = GetWeatherUseCase(
            repository = repository,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun test_get_weather_success() = runBlockingTest {

        val weatherItem = TestDataGenerator.generateWeather()
        val weatherFlow = flowOf(Resource.Success(weatherItem))

        // Given
        coEvery { repository.getWeather("cairo") } returns weatherFlow

        // When & Assertions
        val result = getWeatherUseCase.execute("cairo")
        result.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData).isEqualTo(weatherItem)
            expectComplete()
        }

        // Then
        coVerify { repository.getWeather("cairo") }

    }




    @Test
    fun test_get_weather_fail() = runBlockingTest {

        val errorFlow = flowOf(Resource.Error(Exception()))

        // Given
        coEvery { repository.getWeather("cairo") } returns errorFlow

        // When & Assertions
        val result = getWeatherUseCase.execute("cairo")
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { repository.getWeather("cairo") }

    }



    @Test
    fun test_get_weather_fail_pass_paameter_with_null() = runBlockingTest {

        val errorFlow = flowOf(Resource.Error(Exception()))

        // When & Assertions
        val result = getWeatherUseCase.execute(null)
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then

//        coVerify { repository.getWeather() }

    }
}