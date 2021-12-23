package com.example.remote.model

data class WeatherNetwork(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)