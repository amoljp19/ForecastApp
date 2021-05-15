package com.softaai.forecastapp.model.history


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "deg")
    val deg: Int,
    @Json(name = "gust")
    val gust: Int,
    @Json(name = "speed")
    val speed: Double
)