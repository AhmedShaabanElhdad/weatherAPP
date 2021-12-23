package com.example.local.utils

import com.example.local.model.WeatherLocalModel

/**
 * Dummy data generator for tests
 */
class TestData {

    companion object {
        fun generateWeatherItems() : List<WeatherLocalModel> {
            val item1 = WeatherLocalModel(1, "London", "desc London 1",285.67,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
            val item2 = WeatherLocalModel(1, "Paris", "desc Paris 1",285.67,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
            val item3 = WeatherLocalModel(1, "Cairo", "desc Cairo 1",285.67,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
            return listOf(item1, item2, item3)
        }

        fun generateWeatherItem() : WeatherLocalModel {
            return WeatherLocalModel(1, "London", "desc London 1",285.67,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
        }
    }

}