package com.softaai.forecastapp.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse


@Database(entities = [TodaysForecastApiResponse::class, FiveDaysForecastApiResponse::class], version = 1, exportSchema = false)
@TypeConverters(TodaysForecastConverters::class, FiveDaysForecastConverters::class)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun getTodaysForecastDao(): TodaysForecastDao

    abstract fun getFiveDaysForecastDao(): FiveDaysForecastDao

    companion object {
        const val DB_NAME = "forecast_database"

        @Volatile
        private var INSTANCE: ForecastDatabase? = null

        fun getInstance(context: Context): ForecastDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ForecastDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}