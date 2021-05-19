package com.softaai.forecastapp.forecast.todays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softaai.forecastapp.data.network.State
import com.softaai.forecastapp.data.repository.TodaysForecastRepository
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodaysForecastViewModel @Inject constructor(private val todaysForecastRepository: TodaysForecastRepository) :
    ViewModel() {

    private val _todaysForecastLiveData = MutableLiveData<State<TodaysForecastApiResponse>>()

    val todaysForecastLiveData: LiveData<State<TodaysForecastApiResponse>> = _todaysForecastLiveData

    fun getTodaysForecast() {
        viewModelScope.launch {
            todaysForecastRepository.getTodaysForecast()
                .onStart { _todaysForecastLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _todaysForecastLiveData.value = state }
        }
    }
}

