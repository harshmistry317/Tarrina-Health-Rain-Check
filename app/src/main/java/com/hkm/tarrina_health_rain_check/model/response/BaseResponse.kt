package com.hkm.tarrina_health_rain_check.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseResponse : Parcelable {
    var status: Int? = null
    var message: String? = ""
}