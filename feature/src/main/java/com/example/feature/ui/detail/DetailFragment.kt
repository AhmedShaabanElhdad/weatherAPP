package com.example.feature.ui.detail

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.example.base.BaseFragment
import com.example.common.loadImagesWithGlideExt
import com.example.feature.R
import com.example.feature.R.*
import com.example.feature.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Detail Fragment
 */
@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val args : DetailFragmentArgs by navArgs()


    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {




        args.weather?.let { weather ->
            binding.tvWeatherCity.text = weather.name
            binding.tvWeatherDate.text = weather.date
            binding.tvWeatherDesc.text = weather.desc
            binding.tvWeatherWind.text = weather.wind_spead

            binding.tvWeatherTemp.text = weather.temp+ 0x00B0.toChar() + "C"
            binding.maxWeatherTemp.text = weather.temp_max+ 0x00B0.toChar() + "C"
            binding.tvWeatherMinTemp.text = weather.temp_min+ 0x00B0.toChar() + "C"


            val iconUrl = "https://openweathermap.org/img/w/${weather.icon}.png"
            binding.imgIcon.loadImagesWithGlideExt(iconUrl)

        }
    }


}