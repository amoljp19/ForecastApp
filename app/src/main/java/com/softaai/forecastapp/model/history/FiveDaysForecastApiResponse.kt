package com.softaai.forecastapp.model.history


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FiveDaysForecastApiResponse(
    @Json(name = "city")
    val city: City,
    @Json(name = "cnt")
    val cnt: Int,
    @Json(name = "cod")
    val cod: String,
    @Json(name = "list")
    val list: List<Info>,
    @Json(name = "message")
    val message: Int
)