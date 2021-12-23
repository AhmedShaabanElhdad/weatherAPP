package com.example.remote.utils

import com.example.remote.model.*

/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateWeather(): WeatherResponseNetwork {
            return WeatherResponseNetwork(
                coord = CoordNetwork(
                    lat = -0.1257,
                    lon = 51.5085
                ),
                weather = listOf(
                    WeatherNetwork(
                        id = 804,
                        main = "Clouds",
                        description = "overcast clouds",
                        icon = "04d"
                    )
                ),
                base = "stations",
                main = MainNetwork(
                    temp = 285.67,
                    feels_like = 284.94,
                    temp_min = 284.65,
                    temp_max = 286.63,
                    pressure = 1017,
                    humidity = 75
                ),

                visibility = 10000,
                wind = WindNetwork(
                    speed = 4.63,
                    deg = 300,
                    gust = 0.0
                ),
                clouds = CloudsNetwork(
                    all = 90
                ),
                dt = 1636814662,
                sys = SysNetwork(
                    type = 2,
                    id = 2019646,
                    country = "GB",
                    sunrise = 1636787720,
                    sunset = 1636820067
                ),
        timezone= 0,
        id=2643743,
        name="London",
        cod=200
    )
}
}

}