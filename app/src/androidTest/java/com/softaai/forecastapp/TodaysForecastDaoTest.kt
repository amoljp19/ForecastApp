package com.softaai.forecastapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import com.softaai.forecastapp.data.persistence.ForecastDatabase
import com.softaai.forecastapp.model.todays.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test

class TodaysForecastDaoTest {

    private lateinit var mDatabase: ForecastDatabase

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ForecastDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_get_todays_forecast() = runBlocking {

        val todaysForecastApiResponse = TodaysForecastApiResponse(
            "test1",
            Clouds(1),
            1,
            Coord(1,1),
            1,
            1,
            Main(1.0, 1, 1, 1, 1, 1.0, 1.0, 1.0),
            "test1",
            Rain(1.0),
            Sys(1, 1),
            1,
            1,
            listOf(Weather("test1", "test1", 1, "test1")),
            Wind(1, 1.0, 1.0)
        )

        mDatabase.getTodaysForecastDao().addTodaysForecastResponse(todaysForecastApiResponse)

        val dbTodaysForecastApiResponse = mDatabase.getTodaysForecastDao().getTodaysForecastResponse()

        ViewMatchers.assertThat(dbTodaysForecastApiResponse, CoreMatchers.equalTo(todaysForecastApiResponse))
    }


    @After
    fun cleanup() {
        mDatabase.close()
    }

}