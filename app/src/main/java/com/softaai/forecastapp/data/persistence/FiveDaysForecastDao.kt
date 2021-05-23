package com.softaai.forecastapp.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface FiveDaysForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFiveDaysForecastResponse(fiveDaysForecastApiResponse: FiveDaysForecastApiResponse)

    @Query("SELECT * FROM ${FiveDaysForecastApiResponse.TABLE_NAME}")
    fun getFiveDaysForecastResponse(): Flow<FiveDaysForecastApiResponse>

    @Query("DELETE FROM ${FiveDaysForecastApiResponse.TABLE_NAME}")
    suspend fun deleteFiveDaysForecastResponse()

}