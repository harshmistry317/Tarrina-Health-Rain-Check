package com.hkm.tarrina_health_rain_check.model.response


import com.google.gson.annotations.SerializedName

data class ReverseGeocodingResponse(
    @SerializedName("place_id")
    val placeId: String?,
    val licence: String?,
    @SerializedName("osm_type")
    val osmType: String?,
    @SerializedName("osm_id")
    val osmId: String?,
    val lat: String?,
    val lon: String?,
    @SerializedName("display_name")
    val displayName: String?,
    val address: AddressData?,
):BaseResponse()

data class AddressData(
    @SerializedName("house_number")
    val houseNumber: String?,
    val road: String?,
    val neighbourhood: String?,
    val city: String?,
    val county: String?,
    val state: String?,
    val postcode: String?,
    @SerializedName("country_code")
    val countryCode: String?
)
