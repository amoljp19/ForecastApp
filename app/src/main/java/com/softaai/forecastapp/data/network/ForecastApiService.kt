package com.softaai.forecastapp.data.network

import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ForecastApiService {

    @GET("weather?lat=0&lon=0&appid=fae7190d7e6433ec3a45285ffcf55c86")
    suspend fun getTodaysForecast(): Response<TodaysForecastApiResponse>

    @GET("forecast?lat=0&lon=0&appid=fae7190d7e6433ec3a45285ffcf55c86")
    suspend fun getFiveDaysForecast(): Response<FiveDaysForecastApiResponse>


    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "fae7190d7e6433ec3a45285ffcf55c86"
    }
}