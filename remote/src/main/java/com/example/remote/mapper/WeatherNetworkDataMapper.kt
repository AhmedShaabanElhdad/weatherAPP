package com.example.remote.mapper

import com.example.common.Mapper
import com.example.data.model.WeatherDTO
import com.example.remote.model.WeatherResponseNetwork
import javax.inject.Inject


class WeatherNetworkDataMapper @Inject constructor() :
    Mapper<WeatherResponseNetwork, WeatherDTO> {

    override fun from(i: WeatherResponseNetwork?): WeatherDTO {
        return WeatherDTO(
            id = i?.id ?: 0,
            name = i?.name ?: "",
            desc = i?.weather?.get(0)?.description ?: "",
            temp = i?.main?.temp ?: 0.0,
            icon = i?.weather?.get(0)?.icon ?: "",
            feels_like = i?.main?.feels_like ?: 0.0,
            temp_min = i?.main?.temp_min ?: 0.0,
            temp_max = i?.main?.temp_max ?: 0.0,
            pressure = i?.main?.pressure ?: 0,
            humidity = i?.main?.humidity ?: 0,
            wind_spead = i?.wind?.speed ?: 0.0,
            sunrise = i?.sys?.sunrise ?: 0,
            sunset = i?.sys?.sunset ?: 0,
            dt = i?.dt ?: 0,
        )
    }

    override fun to(o: WeatherDTO?): WeatherResponseNetwork {
        return WeatherResponseNetwork()
    }
}