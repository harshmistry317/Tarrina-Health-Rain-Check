package com.hkm.tarrina_health_rain_check.screens.landing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.hkm.tarrina_health_rain_check.data.datasource.RainCheckDataSource
import com.hkm.tarrina_health_rain_check.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RainCheckLandingViewModel @Inject constructor(
    private val application: Application,
    private val rainCheckDataSource: RainCheckDataSource
) : AndroidViewModel(application){

    private val _state = MutableStateFlow<RainCheckLandingState>(RainCheckLandingState())
    val state = _state.asStateFlow()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)


    fun onAction(action: RainCheckLandingAction){
        when(action){
            RainCheckLandingAction.OnButtonClick -> {
                fetchUserLocation(
                    onSuccess = { lat,long->
                        reversGeocoder(lat = lat, lon = long)
                    },
                    onError = {error->
                        _state.update {
                            it.copy(isLoading = false, errorText = error)
                        }
                    }
                )
            }
        }
    }

    fun updateState(sate : RainCheckLandingState){
     _state.update {
         sate
     }
    }

    fun fetchUserLocation(onSuccess: (Double, Double) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(isLoading = true, errorText = null)
                }
                val cancellationTokenSource = CancellationTokenSource()
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token).addOnSuccessListener {location->
                    if (location != null) {
                        onSuccess(location.latitude, location.longitude)
                    } else {
                        onError("Location is null")
                    }
                }.addOnFailureListener {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            onSuccess(location.latitude, location.longitude)
                        } else {
                            onError("Location is null")
                        }
                    }.addOnFailureListener {
                        onError("Failed to get location: ${it.message}")
                    }
                }

            } catch (e: SecurityException) {
                onError("Permission not granted: ${e.message}")
            }
        }

    }

    fun reversGeocoder(lat:Double,lon:Double){
        viewModelScope.launch {
            val response = rainCheckDataSource.reversGeocoder(lat,lon)
            when(response){
                is NetworkResult.Error<*> -> {

                }
                is NetworkResult.Idle<*> -> {

                }
                is NetworkResult.Loading<*> -> {

                }
                is NetworkResult.Success<*> ->{

                }
            }
        }

    }

}