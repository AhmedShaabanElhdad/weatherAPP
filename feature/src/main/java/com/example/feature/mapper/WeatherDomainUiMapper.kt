package com.example.feature.mapper

import com.example.common.Mapper
import com.example.common.getFormatTime
import com.example.domain.entity.WeatherEntity
import com.example.feature.model.WeatherUiModel
import javax.inject.Inject


class WeatherDomainUiMapper @Inject constructor() : Mapper<WeatherEntity, WeatherUiModel> {


    override fun from(i: WeatherEntity?): WeatherUiModel {
//        val celTemp = i?.temp?.toFloat()?.let { (it - 32.0f) * 5.0f / 9.0f }
        return  WeatherUiModel(
            id = i?.id?:0,
            name = i?.name?:"",
            desc = i?.desc?:"",
            temp = "${i?.temp}",
            icon = i?.icon?:"",
            feels_like = "${i?.feels_like}",
            temp_min = "${i?.temp_min}",
            temp_max = "${i?.temp_max}",
            pressure = "${i?.pressure}",
            humidity = "${i?.humidity}",
            wind_spead = "${i?.wind_spead}",
            sunrise = i?.sunrise?.getFormatTime() ?:"",
            sunset = i?.sunset?.getFormatTime() ?:"",
            date = i?.dt?.getFormatTime() ?:"",


        )
    }

    override fun to(o: WeatherUiModel?): WeatherEntity {
        return  WeatherEntity(
            id = o?.id?:0,
            name = o?.name?:"",
            desc = o?.desc?:"",
            temp = o?.temp?.toDouble() ?:0.0,
            icon = o?.icon ?:"",
            feels_like = o?.feels_like?.toDouble()?:0.0,
            temp_min = o?.temp_min?.toDouble()?:0.0,
            temp_max = o?.temp_max?.toDouble()?:0.0,
            pressure = o?.pressure?.toInt()?:0,
            humidity = o?.humidity?.toInt()?:0,
            wind_spead = o?.wind_spead?.toDouble()?:0.0,
            sunrise = o?.sunrise?.toLong()?:0,
            sunset = o?.sunset?.toLong()?:0,
            dt = o?.date?.toLong()?:0,
        )
    }
}