package com.softaai.forecastapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import com.softaai.forecastapp.data.persistence.ForecastDatabase
import com.softaai.forecastapp.model.fivedays.City
import com.softaai.forecastapp.model.fivedays.Clouds
import com.softaai.forecastapp.model.fivedays.Coord
import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse
import com.softaai.forecastapp.model.fivedays.Info
import com.softaai.forecastapp.model.fivedays.Main
import com.softaai.forecastapp.model.fivedays.Rain
import com.softaai.forecastapp.model.fivedays.Sys
import com.softaai.forecastapp.model.fivedays.Weather
import com.softaai.forecastapp.model.fivedays.Wind
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class FiveDaysForecastDaoTest {

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
    fun insert_and_get_five_days_forecast() = runBlocking {

        val fivedaysForecastApiResponse = FiveDaysForecastApiResponse(
            1,
            City(Coord(1,1), "Test1", 1, "Test1", 1, 1, 1, 1),
            1,
            "Test 1",
            listOf(
                Info(
                    Clouds(1),
                    1,
                    "",
                    Main(1.0, 1, 1, 1, 1, 1.0, 1, 1.0, 1.0),
                    1.0,
                    Rain(1.0),
                    Sys("Test1"),
                    1,
                    listOf(Weather("Test1", "Test1", 1, "Test1")),
                    Wind(1, 1, 1.0)
                )
            ),
            1
        )
        mDatabase.getFiveDaysForecastDao().addFiveDaysForecastResponse(fivedaysForecastApiResponse)

        val dbFiveDaysForecastApiResponse =
            mDatabase.getFiveDaysForecastDao().getFiveDaysForecastResponse()

        ViewMatchers.assertThat(
            dbFiveDaysForecastApiResponse,
            CoreMatchers.equalTo(fivedaysForecastApiResponse)
        )
    }


    @After
    fun cleanup() {
        mDatabase.close()
    }


}