package com.example.remote.source

import com.example.common.Mapper
import com.example.data.model.WeatherDTO
import com.example.data.repository.RemoteDataSource
import com.example.remote.api.ApiService
import com.example.remote.model.WeatherResponseNetwork
import javax.inject.Inject


class RemoteDataSourceImp @Inject constructor(
    private val apiService : ApiService,
    private val weatherMapper : Mapper<WeatherResponseNetwork, WeatherDTO>,
    ) : RemoteDataSource {


    override suspend fun getWeather(city:String): WeatherDTO {
        val networkData = apiService.getWeather(city = city)
        return weatherMapper.from(networkData)
    }
}