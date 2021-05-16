package com.softaai.forecastapp.di.module

import android.app.Application
import com.softaai.forecastapp.data.persistence.ForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ForecastDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = ForecastDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideTodaysForecastDao(database: ForecastDatabase) = database.getTodaysForecastDao()


    @Singleton
    @Provides
    fun provideFiveDaysForecastDao(database: ForecastDatabase) = database.getFiveDaysForecastDao()
}