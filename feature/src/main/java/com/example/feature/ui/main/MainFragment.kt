package com.example.feature.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.base.BaseFragment
import com.example.feature.R
import com.example.feature.databinding.FragmentMainBinding
import com.example.feature.ui.contract.MainContract
import com.example.feature.ui.vm.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException


/**
 * Main Fragment
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: WeatherAdapter by lazy {
        WeatherAdapter { weather ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(weather)
            findNavController().navigate(action)
        }
    }



    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext().applicationContext)
    }

    private var cancellationTokenSource = CancellationTokenSource()

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.rvWeather.adapter = adapter


        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager



        binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val city = binding.etSearch.text.toString()
                    if (city.isNotEmpty()) {
                        viewModel.setEvent(MainContract.Event.OnFetchWeather(city))
                        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    }else
                        binding.etSearch.error = getString(R.string.invalid_city)
                    return true
                }
                return false
            }
        })

        initObservers()


        getLocation()

    }

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (val state = it.weatherState) {
                        is MainContract.WeatherState.Idle -> {
                            binding.loadingPb.isVisible = false
                        }
                        is MainContract.WeatherState.Loading -> {
                            binding.loadingPb.isVisible = true
                        }
                        is MainContract.WeatherState.Success -> {
                            val data = state.weatherList
                            adapter.submitList(data)
                            binding.loadingPb.isVisible = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is MainContract.Effect.ShowError -> {
                            val msg = it.message
                        }
                    }
                }
            }
        }
    }





    fun getLocation() {


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
            return
        }


        val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(
            PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )

        currentLocationTask.addOnCompleteListener { task: Task<Location> ->
            val result = if (task.isSuccessful) {
                val result: Location = task.result
                "Location (success): ${result.latitude}, ${result.longitude}"

                city = getCityFromLatAndLong(result.latitude, result.longitude)
                binding.etSearch.setText(city)
                viewModel.setEvent(MainContract.Event.OnFetchWeather(city))
            } else {
                val exception = task.exception
                "Location (failure): $exception"
            }

        }

//        locationManager!!.requestLocationUpdates(
//            LocationManager.NETWORK_PROVIDER,
//            0L,
//            0f,
//            locationListener
//        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLocation()
                PackageManager.PERMISSION_DENIED -> Toast.makeText(
                    requireContext(),
                    getString(R.string.required_location),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Cancels location request (if in flight).
        cancellationTokenSource.cancel()
    }

    var city: String =""
    private fun getCityFromLatAndLong(latitude: Double, longitude: Double): String {
        var city = ""
        try {
            val addresses = Geocoder(requireContext()).getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) { //prevent from error
                //sometimes the city comes in locality, sometimes in subAdminArea.
                if (addresses[0].locality == null) {

                    city = addresses[0].subAdminArea
                } else {
                    city = addresses[0].locality
                }

            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        return city
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }


}