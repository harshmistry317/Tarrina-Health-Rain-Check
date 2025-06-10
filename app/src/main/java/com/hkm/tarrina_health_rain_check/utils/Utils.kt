package com.hkm.tarrina_health_rain_check.utils

import android.content.Context
import android.net.ConnectivityManager

object Utils {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}