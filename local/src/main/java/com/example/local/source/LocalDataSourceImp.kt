package com.example.local.source

import com.example.common.Mapper
import com.example.data.model.WeatherDTO
import com.example.data.repository.LocalDataSource
import com.example.local.database.WeatherDAO
import com.example.local.model.WeatherLocalModel
import javax.inject.Inject

/**
 * Implementation of [LocalDataSource] Source
 */
class LocalDataSourceImp @Inject constructor(
    private val weatherDAO: WeatherDAO,
    private val weatherMapper : Mapper<WeatherLocalModel, WeatherDTO>
) : LocalDataSource {


    override suspend fun addItem(weather: WeatherDTO): Long {
        val weatherLocalModel = weatherMapper.to(weather)
        return weatherDAO.addWeatherItem(weather = weatherLocalModel)
    }

    override suspend fun getItem(name: String): WeatherDTO {
        val weatherLocalModel = weatherDAO.getWeatherItem(name = name)
        return weatherMapper.from(weatherLocalModel)
    }

    override suspend fun addItems(weahterList: List<WeatherDTO>): List<Long> {
        val weatherLocalList = weatherMapper.toList(weahterList)
        return weatherDAO.addWeatherItems(weather = weatherLocalList)
    }

    override suspend fun getItems(): List<WeatherDTO> {

        val weatherLocalList = weatherDAO.getWeatherItems()
        return weatherMapper.fromList(weatherLocalList)
    }

    override suspend fun updateItem(weather: WeatherDTO): Int {
        val weatherLocalModel = weatherMapper.to(weather)
        return weatherDAO.updateWeatherItem(weather = weatherLocalModel)
    }

    override suspend fun deleteItemById(id: Int): Int {
        return weatherDAO.deleteWeatherItemById(id = id)
    }

    override suspend fun deleteItem(weather: WeatherDTO): Int {
        val weatherLocalModel = weatherMapper.to(weather)
        return weatherDAO.deleteWeatherItem(weather = weatherLocalModel)
    }

    override suspend fun clearCachedItems(): Int {
        return weatherDAO.clearCachedWeatherItems()
    }


}