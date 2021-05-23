package com.softaai.forecastapp.data.repository

import com.softaai.forecastapp.data.network.ForecastApiService
import com.softaai.forecastapp.data.network.Resource
import com.softaai.forecastapp.data.persistence.TodaysForecastDao
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

interface TodaysForecastRepository {
    fun getTodaysForecast(): Flow<Resource<TodaysForecastApiResponse>>
}

class DefaultTodaysForecastRepository @Inject constructor(
    private val todaysForecastDao: TodaysForecastDao,
    private val forecastApiService: ForecastApiService
) : TodaysForecastRepository {

    override fun getTodaysForecast(): Flow<Resource<TodaysForecastApiResponse>> {
        return object :
            NetworkBoundRepository<TodaysForecastApiResponse, TodaysForecastApiResponse>() {

            override suspend fun saveRemoteData(response: TodaysForecastApiResponse) =
                todaysForecastDao.addTodaysForecastResponse(response)

            override fun fetchFromLocal(): Flow<TodaysForecastApiResponse> =
                todaysForecastDao.getTodaysForecastResponse()

            override suspend fun fetchFromRemote(): Response<TodaysForecastApiResponse> =
                forecastApiService.getTodaysForecast()

        }.asFlow()
    }

}