package com.softaai.forecastapp.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse


@Dao
interface TodaysForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodaysForecastResponse(todaysForecastApiResponse: TodaysForecastApiResponse)

    @Query("DELETE FROM ${TodaysForecastApiResponse.TABLE_NAME}")
    suspend fun deleteTodaysForecastResponse()
}