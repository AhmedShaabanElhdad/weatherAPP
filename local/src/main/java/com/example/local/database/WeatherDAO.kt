package com.example.local.database

import androidx.room.*
import com.example.local.model.WeatherLocalModel

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherItem(weather : WeatherLocalModel) : Long

    @Query("SELECT * FROM weather WHERE name = :name")
    suspend fun getWeatherItem(name: String): WeatherLocalModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherItems(weather: List<WeatherLocalModel>) : List<Long>

    @Query("SELECT * FROM weather")
    suspend fun getWeatherItems(): List<WeatherLocalModel>

    @Update
    suspend fun updateWeatherItem(weather: WeatherLocalModel): Int

    @Query("DELETE FROM weather WHERE id = :id")
    suspend fun deleteWeatherItemById(id : Int) : Int

    @Delete
    suspend fun deleteWeatherItem(weather : WeatherLocalModel) : Int

    @Query("DELETE FROM weather")
    suspend fun clearCachedWeatherItems(): Int
}