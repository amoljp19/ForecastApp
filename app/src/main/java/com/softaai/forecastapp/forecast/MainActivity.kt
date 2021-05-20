package com.softaai.forecastapp.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.softaai.forecastapp.R
import com.softaai.forecastapp.data.network.State
import com.softaai.forecastapp.forecast.fivedays.FiveDaysForecastViewModel
import com.softaai.forecastapp.forecast.todays.TodaysForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val todaysForecastViewModel: TodaysForecastViewModel by viewModels()

    val fiveDaysForecastViewModel: FiveDaysForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        getAndObserveTodaysForecast()
        getAndObserveFiveDaysForecast()
    }

    private fun getAndObserveTodaysForecast(){
        todaysForecastViewModel.todaysForecastLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                getTodaysForecast()
            }
        }
        observeTodaysForecast()
    }

    private fun getAndObserveFiveDaysForecast(){
        fiveDaysForecastViewModel.fiveDaysForecastLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                getFiveDaysForecast()
            }
        }
        observeFiveDaysForecast()
    }


    private fun getTodaysForecast() = todaysForecastViewModel.getTodaysForecast()

    private fun getFiveDaysForecast() = fiveDaysForecastViewModel.getFiveDaysForecast()

    private fun observeTodaysForecast() {
        todaysForecastViewModel.todaysForecastLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> Toast.makeText(applicationContext, "Loading...", Toast.LENGTH_SHORT).show()
                is State.Success -> {
                    if (!state.data.equals(null)) {
                        Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                    }
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeFiveDaysForecast() {
        fiveDaysForecastViewModel.fiveDaysForecastLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> Toast.makeText(applicationContext, "Loading...", Toast.LENGTH_SHORT).show()
                is State.Success -> {
                    if (!state.data.equals(null)) {
                        Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                    }
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}