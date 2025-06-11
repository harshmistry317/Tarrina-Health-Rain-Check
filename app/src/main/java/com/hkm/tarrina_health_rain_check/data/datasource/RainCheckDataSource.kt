package com.hkm.tarrina_health_rain_check.data.datasource

import android.app.Application
import com.hkm.tarrina_health_rain_check.data.network.RainCheckApi
import com.hkm.tarrina_health_rain_check.model.response.ReverseGeocodingResponse
import com.hkm.tarrina_health_rain_check.utils.BaseDataSource
import com.hkm.tarrina_health_rain_check.utils.NetworkResult
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class RainCheckDataSource @Inject constructor(
    private val application: Application,
    private val rainCheckApi: RainCheckApi
) : BaseDataSource(application){

    suspend fun reversGeocoder(lat : Double, lon: Double): NetworkResult<ReverseGeocodingResponse> {
        return safeApiCall { rainCheckApi.reversGeocoder(lat = lat, lon = lon) }
    }

}