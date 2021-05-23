package com.softaai.forecastapp.forecast.todays

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.softaai.forecastapp.data.network.Resource
import com.softaai.forecastapp.data.network.State
import com.softaai.forecastapp.data.repository.TodaysForecastRepository
import com.softaai.forecastapp.forecast.CoroutineTestRule
import com.softaai.forecastapp.model.todays.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class TodaysForecastViewModelTest {

    @Mock
    private lateinit var todaysForecastViewModel: TodaysForecastViewModel

    @Mock
    private lateinit var todaysForecastRepository: TodaysForecastRepository

    @Mock
    private lateinit var observer: Observer<State<TodaysForecastApiResponse>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()


    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        todaysForecastViewModel = spy(TodaysForecastViewModel(todaysForecastRepository))

    }


    @ExperimentalCoroutinesApi
    @Test
    fun todaysForecastLiveData_ShouldPostLoading() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            //Given
            val data = mock<Flow<Resource<TodaysForecastApiResponse>>>()

            whenever(todaysForecastRepository.getTodaysForecast()).thenReturn(data)


            //When
            todaysForecastViewModel.getTodaysForecast()

            //Then
            todaysForecastViewModel.todaysForecastLiveData.observeForever(observer)
            verify(observer).onChanged(ArgumentMatchers.refEq(State.loading()))


        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun todaysForecastLiveData_ShouldPostSuccess() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            val todaysForecastApiResponse = TodaysForecastApiResponse(
                "test1",
                Clouds(1),
                1,
                Coord(1, 1),
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

            whenever(todaysForecastRepository.getTodaysForecast()) doReturn flowOf(
                Resource.Success(
                    todaysForecastApiResponse
                )
            )

            todaysForecastViewModel.getTodaysForecast()


            val observer = object : Observer<State<TodaysForecastApiResponse>> {
                override fun onChanged(data1: State<TodaysForecastApiResponse>) {
                    assertThat(data1, IsEqual(State.success(todaysForecastApiResponse)))
                    todaysForecastViewModel.todaysForecastLiveData.removeObserver(this)
                }
            }
            todaysForecastViewModel.todaysForecastLiveData.observeForever(observer)

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun todaysForecastLiveData_ShouldPostError() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            val message = "error message"

            whenever(todaysForecastRepository.getTodaysForecast()) doReturn flowOf(
                Resource.Failed(
                    message
                )
            )

            todaysForecastViewModel.getTodaysForecast()


            val observer = object : Observer<State<TodaysForecastApiResponse>> {
                override fun onChanged(data1: State<TodaysForecastApiResponse>) {
                    assertThat(data1, IsEqual(State.error(message)))
                    todaysForecastViewModel.todaysForecastLiveData.removeObserver(this)
                }
            }
            todaysForecastViewModel.todaysForecastLiveData.observeForever(observer)

        }

    }

}