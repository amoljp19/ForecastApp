package com.softaai.forecastapp.forecast.todays

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.softaai.forecastapp.data.network.State
import com.softaai.forecastapp.data.repository.TodaysForecastRepository
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class TodaysForecastViewModelTest {

    private val _todaysForecastLiveData = MutableLiveData<State<TodaysForecastApiResponse>>()

    //val todaysForecastLiveData: LiveData<State<TodaysForecastApiResponse>> = _todaysForecastLiveData

//    fun getTodaysForecast() {
//        viewModelScope.launch {
//            todaysForecastRepository.getTodaysForecast()
//                .onStart { _todaysForecastLiveData.value = State.loading() }
//                .map { resource -> State.fromResource(resource) }
//                .collect { state -> _todaysForecastLiveData.value = state }
//        }
//    }

    @Mock
    private lateinit var todaysForecastViewModel: TodaysForecastViewModel

    @Mock
    private lateinit var todaysForecastRepository: TodaysForecastRepository

    @Mock
    private lateinit var todaysForecastLiveData: LiveData<State<TodaysForecastApiResponse>>

    @Mock
    private lateinit var observer: Observer<in State<TodaysForecastApiResponse>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        todaysForecastViewModel = spy(TodaysForecastViewModel(todaysForecastRepository))
        todaysForecastLiveData = todaysForecastViewModel.todaysForecastLiveData
    }


    @Test
    fun `Verify livedata values changes on event`(){
        assertNotNull(todaysForecastViewModel.getTodaysForecast())
        todaysForecastViewModel.todaysForecastLiveData.observeForever(observer)
        verify(observer).onChanged(State.loading())
        todaysForecastViewModel.getTodaysForecast()
    }

}