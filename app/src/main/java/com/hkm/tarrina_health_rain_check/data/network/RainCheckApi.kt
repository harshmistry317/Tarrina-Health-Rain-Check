package com.hkm.tarrina_health_rain_check.data.network

import com.hkm.tarrina_health_rain_check.model.response.ReverseGeocodingResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface RainCheckApi {
    companion object{
        const val REVERS_GEOCODER = "https://nominatim.openstreetmap.org/reverse"
    }

    @GET(REVERS_GEOCODER)
    suspend fun reversGeocoder(
        @Query("format") format: String = "json",
        @Query("addressdetails") addressDetails: Int = 1,
        @Query ("lat") lat : Double,
        @Query("lon") lon: Double
    ): Response<ReverseGeocodingResponse>
}