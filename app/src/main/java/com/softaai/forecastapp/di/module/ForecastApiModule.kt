package com.softaai.forecastapp.di.module

import com.softaai.forecastapp.data.network.ForecastApiService
import com.softaai.forecastapp.data.network.RequestInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ForecastApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().addInterceptor(RequestInterceptor()).build();


    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): ForecastApiService = Retrofit.Builder()
        .baseUrl(ForecastApiService.BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .client(okHttpClient)
        .build()
        .create(ForecastApiService::class.java)

}