package com.softaai.forecastapp.forecast.city.fivedays

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.softaai.forecastapp.data.network.Resource
import com.softaai.forecastapp.data.network.State
import com.softaai.forecastapp.data.repository.FiveDaysForecastRepository
import com.softaai.forecastapp.forecast.CoroutineTestRule
import com.softaai.forecastapp.model.fivedays.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class FiveDaysForecastViewModelTest {

    @Mock
    private lateinit var fiveDaysForecastViewModel: FiveDaysForecastViewModel

    @Mock
    private lateinit var fiveDaysForecastRepository: FiveDaysForecastRepository

    @Mock
    private lateinit var observer: Observer<State<FiveDaysForecastApiResponse>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()


    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        fiveDaysForecastViewModel =
            Mockito.spy(FiveDaysForecastViewModel(fiveDaysForecastRepository))

    }


    @ExperimentalCoroutinesApi
    @Test
    fun fiveDaysForecastLiveData_ShouldPostLoading() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            //Given
            val data = mock<Flow<Resource<FiveDaysForecastApiResponse>>>()

            whenever(fiveDaysForecastRepository.getFiveDaysForecast()).thenReturn(data)


            //When
            fiveDaysForecastViewModel.getFiveDaysForecast()

            //Then
            fiveDaysForecastViewModel.fiveDaysForecastLiveData.observeForever(observer)
            Mockito.verify(observer).onChanged(ArgumentMatchers.refEq(State.loading()))


        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun fiveDaysForecastLiveData_ShouldPostSuccess() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            val fiveDaysForecastApiResponse = FiveDaysForecastApiResponse(
                1,
                City(Coord(1, 1), "Test1", 1, "Test1", 1, 1, 1, 1),
                1,
                "Test 1",
                listOf(
                    Info(
                        Clouds(1),
                        1,
                        "",
                        Main(1.0, 1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0),
                        1.0,
                        Rain(1.0),
                        Sys("Test1"),
                        1,
                        listOf(Weather("Test1", "Test1", 1, "Test1")),
                        Wind(1, 1.0, 1.0)
                    )
                ),
                1
            )


            whenever(fiveDaysForecastRepository.getFiveDaysForecast()) doReturn flowOf(
                Resource.Success(
                    fiveDaysForecastApiResponse
                )
            )

            fiveDaysForecastViewModel.getFiveDaysForecast()


            val observer = object : Observer<State<FiveDaysForecastApiResponse>> {
                override fun onChanged(data1: State<FiveDaysForecastApiResponse>) {
                    MatcherAssert.assertThat(
                        data1,
                        IsEqual(State.success(fiveDaysForecastApiResponse))
                    )
                    fiveDaysForecastViewModel.fiveDaysForecastLiveData.removeObserver(this)
                }
            }
            fiveDaysForecastViewModel.fiveDaysForecastLiveData.observeForever(observer)

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun fiveDaysForecastLiveData_ShouldPostError() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            val message = "error message"

            whenever(fiveDaysForecastRepository.getFiveDaysForecast()) doReturn flowOf(
                Resource.Failed(
                    message
                )
            )

            fiveDaysForecastViewModel.getFiveDaysForecast()


            val observer = object : Observer<State<FiveDaysForecastApiResponse>> {
                override fun onChanged(data1: State<FiveDaysForecastApiResponse>) {
                    MatcherAssert.assertThat(data1, IsEqual(State.error(message)))
                    fiveDaysForecastViewModel.fiveDaysForecastLiveData.removeObserver(this)
                }
            }
            fiveDaysForecastViewModel.fiveDaysForecastLiveData.observeForever(observer)

        }

    }

}