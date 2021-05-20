package com.softaai.forecastapp.di.module

import com.softaai.forecastapp.data.repository.DefaultFiveDaysForecastRepository
import com.softaai.forecastapp.data.repository.DefaultTodaysForecastRepository
import com.softaai.forecastapp.data.repository.FiveDaysForecastRepository
import com.softaai.forecastapp.data.repository.TodaysForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ForecastRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindTodaysForecastRepository(todaysForecastRepository: DefaultTodaysForecastRepository): TodaysForecastRepository


    @ActivityRetainedScoped
    @Binds
    abstract fun bindFiveDaysForecastRepository(fiveDaysForecastRepository: DefaultFiveDaysForecastRepository): FiveDaysForecastRepository

}