package com.example.feature



import com.example.common.Resource
import com.example.domain.usecase.GetWeatherUseCase
import com.example.feature.ui.contract.MainContract
import com.example.feature.mapper.WeatherDomainUiMapper
import com.google.common.truth.Truth
import com.example.feature.utils.MainCoroutineRule
import com.example.feature.utils.TestDataGenerator
import com.example.feature.ui.vm.MainViewModel


import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import app.cash.turbine.test
//import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime



@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class MainViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    private val weatherMapper = WeatherDomainUiMapper()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create MainViewModel before every test
        mainViewModel = MainViewModel(
            savedStateHandle = savedStateHandle,
            getWeatherUseCase = getWeatherUseCase,
            weatherMapper = weatherMapper
        )
    }

    @Test
    fun test_fetch_weatherItems_success() = runBlockingTest {

        val weatherItems = TestDataGenerator.generateWeatherItems()
        val weatherFlow = flowOf(Resource.Success(weatherItems))

        // Given
        coEvery { getWeatherUseCase.execute(any()) } returns weatherFlow

        // When && Assertions
        mainViewModel.uiState.test {
            mainViewModel.setEvent(MainContract.Event.OnFetchWeather("cairo"))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                MainContract.State(
                    weatherState = MainContract.WeatherState.Idle,
                    selectedWeather = null
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                MainContract.State(
                    weatherState = MainContract.WeatherState.Loading,
                    selectedWeather = null
                )
            )
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected.weatherState as MainContract.WeatherState.Success).weatherList
            Truth.assertThat(expected).isEqualTo(
                MainContract.State(
                    weatherState = MainContract.WeatherState.Success(listOf(weatherMapper.from(weatherItems))),
                    selectedWeather = null
                )
            )
            Truth.assertThat(expectedData).containsExactlyElementsIn(listOf(weatherMapper.from(weatherItems)))


            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getWeatherUseCase.execute(any()) }
    }

    @Test
    fun test_fetch_weather_fail() = runBlockingTest {

        val weatherErrorFlow = flowOf(Resource.Error(Exception("error string")))

        // Given
        coEvery { getWeatherUseCase.execute(any()) } returns weatherErrorFlow

        // When && Assertions (UiState)
        mainViewModel.uiState.test {
            // Call method inside of this scope
            mainViewModel.setEvent(MainContract.Event.OnFetchWeather("cairo"))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                MainContract.State(
                    weatherState = MainContract.WeatherState.Idle,
                    selectedWeather = null
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                MainContract.State(
                    weatherState = MainContract.WeatherState.Loading,
                    selectedWeather = null
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // When && Assertions (UiEffect)
        mainViewModel.effect.test {
            // Expect ShowError Effect
            val expected = expectItem()
            val expectedData = (expected as MainContract.Effect.ShowError).message
            Truth.assertThat(expected).isEqualTo(
                MainContract.Effect.ShowError("error string")
            )
            Truth.assertThat(expectedData).isEqualTo("error string")
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getWeatherUseCase.execute(any()) }
    }


    @Test
    fun test_select_weather_item() = runBlockingTest {

        val weather = TestDataGenerator.generateWeatherItems()

        // Given (no-op)

        // When && Assertions
        mainViewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            mainViewModel.setEvent(MainContract.Event.OnWeatherItemClicked(weather = weatherMapper.from(weather)))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                MainContract.State(
                    weatherState = MainContract.WeatherState.Idle,
                    selectedWeather = null
                )
            )
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = expected.selectedWeather
            Truth.assertThat(expected).isEqualTo(
                MainContract.State(
                    weatherState = MainContract.WeatherState.Idle,
                    selectedWeather = weatherMapper.from(weather)
                )
            )
            Truth.assertThat(expectedData).isEqualTo(weatherMapper.from(weather))
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then (no-op)
    }
}