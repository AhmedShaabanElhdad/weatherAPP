package com.example.data.utils

import com.example.data.model.WeatherDTO


class TestDataGenerator {

    companion object {
        fun generateWeather() : WeatherDTO {
            return WeatherDTO(1, "cairo", "desc",299.57,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
        }



        fun generateWeatherItems() : List<WeatherDTO> {
            return listOf(
                WeatherDTO(1, "cairo", "desc",299.57,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691),
                WeatherDTO(1, "cairo", "desc",299.57,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691),
                WeatherDTO(1, "cairo", "desc",299.57,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
            )
        }

    }

}