package com.example.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class WeatherUiModel(
    val id: Int,
    val name: String,
    val desc: String,
    val temp: String,
    val icon: String,
    val feels_like: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val humidity: String,
    val wind_spead: String,
    val sunrise: String,
    val sunset: String,
    val date: String,
) : Parcelable