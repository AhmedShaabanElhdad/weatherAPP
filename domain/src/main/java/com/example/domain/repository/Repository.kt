package com.example.domain.repository

import com.example.common.Resource
import com.example.domain.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

/**
 * Methods of Repository
 */
interface Repository {

    suspend fun getWeather(city:String) : Flow<Resource<WeatherEntity>>

}