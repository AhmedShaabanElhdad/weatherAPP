package com.example.remote.model

data class WeatherResponseNetwork(
    val base: String = "",
    val clouds: CloudsNetwork? = null,
    val cod: Int = 0,
    val coord: CoordNetwork? = null,
    val dt: Long = 0,
    val id: Int = 0,
    val main: MainNetwork? = null,
    val name: String = "",
    val sys: SysNetwork? = null,
    val timezone: Int = 0,
    val visibility: Int = 0,
    val weather: List<WeatherNetwork>? = null,
    val wind: WindNetwork? = null
)