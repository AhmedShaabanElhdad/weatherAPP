package com.example.domain.utils

import com.example.domain.entity.WeatherEntity

class TestDataGenerator {

    companion object {
        fun generateWeatherItems() : List<WeatherEntity> {
            val item1 = WeatherEntity(1, "london", "desc 1", 11.0,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
            val item2 = WeatherEntity(2, "paris", "desc 2", 11.0,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
            val item3 = WeatherEntity(3, "cairo", "desc 3", 11.0,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
            return listOf(item1, item2, item3)
        }

        fun generateWeather() : WeatherEntity {
            return WeatherEntity(1, "london", "desc 1", 11.0,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
        }
    }

}