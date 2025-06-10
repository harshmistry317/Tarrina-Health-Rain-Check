package com.hkm.tarrina_health_rain_check.utils

import android.content.Context
import com.hkm.tarrina_health_rain_check.R
import retrofit2.Response

abstract class BaseDataSource(private val context: Context) {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return if (Utils.isInternetAvailable(context)) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    response.body()?.let {
                        NetworkResult.Success(it)
                    } ?: NetworkResult.Error(context.getString(R.string.something_went_wrong))
                } else {
                    NetworkResult.Error(response.message(), response.code())
                }
            } catch (e: Exception) {
                NetworkResult.Error("Exception: ${e.localizedMessage}")
            }
        } else {
            NetworkResult.Error(context.getString(R.string.no_internet))
        }
    }
}