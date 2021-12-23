package com.example.weatherapplication.di

import com.example.common.Mapper
import com.example.data.mapper.WeatherDataDomainMapper
import com.example.data.model.WeatherDTO
import com.example.domain.entity.WeatherEntity
import com.example.feature.mapper.WeatherDomainUiMapper
import com.example.feature.model.WeatherUiModel
import com.example.local.model.WeatherLocalModel
import com.example.remote.model.WeatherNetwork
import com.example.local.mapper.WeatherLocalDataMapper
import com.example.remote.mapper.WeatherNetworkDataMapper
import com.example.remote.model.WeatherResponseNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that holds Mappers
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    //region Locale Mappers
    @Binds
    abstract fun bindsWeatherLocalDataMapper(mapper : WeatherLocalDataMapper) : Mapper<WeatherLocalModel, WeatherDTO>
    //endregion


    //region Data Mappers
    @Binds
    abstract fun bindsWeatherDataDomainMapper(mapper : WeatherDataDomainMapper) : Mapper<WeatherDTO, WeatherEntity>
    //endregion


    //region Presentation Mappers
    @Binds
    abstract fun bindsWeatherDomainUiMapper(mapper : WeatherDomainUiMapper) : Mapper<WeatherEntity, WeatherUiModel>
    //endregion


    //region Remote Mappers
    @Binds
    abstract fun bindsWeatherNetworkDataMapper(mapper: WeatherNetworkDataMapper): Mapper<WeatherResponseNetwork, WeatherDTO>
    //endregion

}