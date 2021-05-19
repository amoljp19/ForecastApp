package com.softaai.forecastapp.forecast.fivedays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softaai.forecastapp.data.network.State
import com.softaai.forecastapp.data.repository.FiveDaysForecastRepository
import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiveDaysForecastViewModel @Inject constructor(private val fiveDaysForecastRepository: FiveDaysForecastRepository) :
    ViewModel() {

    private val _fiveDaysForecastLiveData = MutableLiveData<State<FiveDaysForecastApiResponse>>()

    val fiveDaysForecastLiveData: LiveData<State<FiveDaysForecastApiResponse>> = _fiveDaysForecastLiveData

    fun getFiveDaysForecast() {
        viewModelScope.launch {
            fiveDaysForecastRepository.getFiveDaysForecast()
                .onStart { _fiveDaysForecastLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _fiveDaysForecastLiveData.value = state }
        }
    }
}
