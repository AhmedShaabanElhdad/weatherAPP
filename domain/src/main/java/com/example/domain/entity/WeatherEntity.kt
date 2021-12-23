package com.example.domain.entity

import java.util.*

data class WeatherEntity(
    val id: Int,
    val name: String,
    val desc: String,
    val temp: Double,
    val icon: String,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val wind_spead: Double,
    val sunrise: Long,
    val sunset: Long,
    val dt: Long,
)