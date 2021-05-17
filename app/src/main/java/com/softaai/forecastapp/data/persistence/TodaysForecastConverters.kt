package com.softaai.forecastapp.data.persistence

import androidx.room.TypeConverter
import com.softaai.forecastapp.model.todays.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class TodaysForecastConverters {

    @TypeConverter
    fun fromStringToClouds(value: String): Clouds? =
        Moshi.Builder().build().adapter(Clouds::class.java).fromJson(value)

    @TypeConverter
    fun fromCloudsToString(clouds: Clouds?): String =
        Moshi.Builder().build().adapter(Clouds::class.java).toJson(clouds)


    @TypeConverter
    fun fromStringToCoord(value: String): Coord? =
        Moshi.Builder().build().adapter(Coord::class.java).fromJson(value)

    @TypeConverter
    fun fromCoordToString(coord: Coord?): String =
        Moshi.Builder().build().adapter(Coord::class.java).toJson(coord)


    @TypeConverter
    fun fromStringToMain(value: String): Main? =
        Moshi.Builder().build().adapter(Main::class.java).fromJson(value)

    @TypeConverter
    fun fromMainToString(main: Main?): String =
        Moshi.Builder().build().adapter(Main::class.java).toJson(main)


    @TypeConverter
    fun fromStringToRain(value: String): Rain? =
        Moshi.Builder().build().adapter(Rain::class.java).fromJson(value)

    @TypeConverter
    fun fromRainToString(rain: Rain?): String =
        Moshi.Builder().build().adapter(Rain::class.java).toJson(rain)


    @TypeConverter
    fun fromStringToSys(value: String): Sys? =
        Moshi.Builder().build().adapter(Sys::class.java).fromJson(value)

    @TypeConverter
    fun fromSysToString(sys: Sys?): String =
        Moshi.Builder().build().adapter(Sys::class.java).toJson(sys)


    @TypeConverter
    fun fromStringToWeatherList(value: String): List<Weather>? =
        Moshi.Builder().build().adapter<List<Weather>>(Types.newParameterizedType(List::class.java, Weather::class.java)).fromJson(value)


    @TypeConverter
    fun fromWeatherListToString(weatherListType: List<Weather>?): String =
        Moshi.Builder().build().adapter<List<Weather>>(Types.newParameterizedType(List::class.java, Weather::class.java)).toJson(weatherListType)



    @TypeConverter
    fun fromStringToWind(value: String): Wind? =
        Moshi.Builder().build().adapter(Wind::class.java).fromJson(value)

    @TypeConverter
    fun fromWindToString(wind: Wind?): String =
        Moshi.Builder().build().adapter(Wind::class.java).toJson(wind)

}