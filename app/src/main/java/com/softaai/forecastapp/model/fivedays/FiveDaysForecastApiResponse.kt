package com.softaai.forecastapp.model.fivedays


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse.Companion.TABLE_NAME
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = TABLE_NAME)
@JsonClass(generateAdapter = true)
data class FiveDaysForecastApiResponse(
        @PrimaryKey
        var id: Int? = 0,
        @Json(name = "city")
        val city: City?,
        @Json(name = "cnt")
        val cnt: Int?,
        @Json(name = "cod")
        val cod: String?,
        @Json(name = "list")
        val list: List<Info>?,
        @Json(name = "message")
        val message: Int?
) {
    companion object {
        const val TABLE_NAME = "five_days_forecast"
    }
}