package com.softaai.forecastapp.model.todays


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lat")
    val lat: Int?,
    @Json(name = "lon")
    val lon: Int?
)