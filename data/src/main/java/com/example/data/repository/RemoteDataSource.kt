package com.example.data.repository

import com.example.data.model.WeatherDTO

/**
 * Methods of Remote Data Source
 */
interface RemoteDataSource {

    suspend fun getWeather(city:String) : WeatherDTO

}