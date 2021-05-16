package com.softaai.forecastapp.model.todays


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse.Companion.TABLE_NAME
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = TABLE_NAME)
@JsonClass(generateAdapter = true)
data class TodaysForecastApiResponse(
        @Json(name = "base")
        val base: String,
        @Json(name = "clouds")
        val clouds: Clouds,
        @Json(name = "cod")
        val cod: Int,
        @Json(name = "coord")
        val coord: Coord,
        @Json(name = "dt")
        val dt: Int,
        @PrimaryKey
        @Json(name = "id")
        val id: Int = 0,
        @Json(name = "main")
        val main: Main,
        @Json(name = "name")
        val name: String,
        @Json(name = "rain")
        val rain: Rain,
        @Json(name = "sys")
        val sys: Sys,
        @Json(name = "timezone")
        val timezone: Int,
        @Json(name = "visibility")
        val visibility: Int,
        @Json(name = "weather")
        val weather: List<Weather>,
        @Json(name = "wind")
        val wind: Wind
) {
    companion object {
        const val TABLE_NAME = "todays_forecast"
    }
}