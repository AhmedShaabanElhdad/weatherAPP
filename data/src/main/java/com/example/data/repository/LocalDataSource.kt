package com.example.data.repository

import com.example.data.model.WeatherDTO


/**
 * Methods of Local Data Source
 */
interface LocalDataSource {

    suspend fun addItem(weather : WeatherDTO) : Long

    suspend fun getItem(name: String): WeatherDTO

    suspend fun addItems(weathers: List<WeatherDTO>) : List<Long>

    suspend fun getItems(): List<WeatherDTO>

    suspend fun updateItem(weather: WeatherDTO): Int

    suspend fun deleteItemById(id : Int) : Int

    suspend fun deleteItem(weather : WeatherDTO) : Int

    suspend fun clearCachedItems(): Int
}