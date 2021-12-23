package com.example.local.utils

import com.example.local.model.WeatherLocalModel

/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateWeatherItems() : List<WeatherLocalModel> {
            val item1 = WeatherLocalModel(1, "London", "desc London 1",285.67)
            val item2 = WeatherLocalModel(1, "Paris", "desc Paris 1",285.67)
            val item3 = WeatherLocalModel(1, "Cairo", "desc Cairo 1",285.67)
            return listOf(item1, item2, item3)
        }

        fun generateWeatherItem() : WeatherLocalModel {
            return WeatherLocalModel(1, "London", "desc London 1",285.67)
        }
    }

}