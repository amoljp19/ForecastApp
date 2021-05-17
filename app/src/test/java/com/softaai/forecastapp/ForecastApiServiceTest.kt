package com.softaai.forecastapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.softaai.forecastapp.data.network.ForecastApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@RunWith(JUnit4::class)
class ForecastApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ForecastApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(ForecastApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getTodaysForeCastResponseTest() = runBlocking {
        enqueueResponse("/todays-api-response.json")
        val todaysForecastResponse = service.getTodaysForecast().body()

        assertThat(todaysForecastResponse, notNullValue())
        assertThat(todaysForecastResponse!!.base, `is`("stations"))
    }


    @Test
    fun getFiveDaysForeCastResponseTest() = runBlocking {
        enqueueResponse("/five-days-api-response.json")
        val fiveDaysForecastResponse = service.getFiveDaysForecast().body()

        assertThat(fiveDaysForecastResponse, notNullValue())
        assertThat(fiveDaysForecastResponse!!.cod, `is`("200"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }

        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )

    }
}