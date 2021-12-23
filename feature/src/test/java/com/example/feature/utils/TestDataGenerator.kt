package com.example.feature.utils

import com.example.domain.entity.WeatherEntity
import com.example.feature.model.WeatherUiModel

class TestDataGenerator {

    companion object {
        fun generateWeatherItems() : WeatherEntity{
            return WeatherEntity(1, "cairo", "desc 1", 12.0005,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
        }
    }

}