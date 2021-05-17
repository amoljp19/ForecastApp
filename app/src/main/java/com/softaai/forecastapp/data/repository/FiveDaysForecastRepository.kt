package com.softaai.forecastapp.data.repository

import com.softaai.forecastapp.data.network.ForecastApiService
import com.softaai.forecastapp.data.network.Resource
import com.softaai.forecastapp.data.persistence.FiveDaysForecastDao
import com.softaai.forecastapp.data.persistence.TodaysForecastDao
import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

interface FiveDaysForecastRepository {
    fun getFiveDaysForecast(): Flow<Resource<FiveDaysForecastApiResponse>>
}

class DefaultFiveDaysForecastRepository @Inject constructor(
    private val fiveDaysForecastDao: FiveDaysForecastDao,
    private val forecastApiService: ForecastApiService
) : FiveDaysForecastRepository {

    override fun getFiveDaysForecast(): Flow<Resource<FiveDaysForecastApiResponse>> {
        return object : NetworkBoundRepository<FiveDaysForecastApiResponse, FiveDaysForecastApiResponse>() {

            override suspend fun saveRemoteData(response: FiveDaysForecastApiResponse) = fiveDaysForecastDao.addFiveDaysForecastResponse(response)

            override fun fetchFromLocal(): Flow<FiveDaysForecastApiResponse> = fiveDaysForecastDao.getFiveDaysForecastResponse()

            override suspend fun fetchFromRemote(): Response<FiveDaysForecastApiResponse> = forecastApiService.getFiveDaysForecast()

        }.asFlow()
    }

}



