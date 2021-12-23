package com.example.feature.ui.main

import com.example.base.BaseViewHolder
import com.example.common.loadImagesWithGlideExt
import com.example.feature.databinding.RowWeatherItemLayoutBinding
import com.example.feature.model.WeatherUiModel

/**
 * ViewHolder class for Weather
 */
class WeatherViewHolder constructor(
    private val binding : RowWeatherItemLayoutBinding,
    private val click : ((WeatherUiModel?) -> Unit)? = null
) : BaseViewHolder<WeatherUiModel, RowWeatherItemLayoutBinding>(binding) {


    init {
        binding.tvSeeMore.setOnClickListener {
            click?.invoke(getRowItem())
        }
    }

    override fun bind() {


        getRowItem()?.let {
            binding.weather = it

            val iconUrl = "https://openweathermap.org/img/w/${it.icon}.png"
            binding.imgIcon.loadImagesWithGlideExt(iconUrl)

            binding.executePendingBindings()

        }
    }
}