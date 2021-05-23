package com.softaai.forecastapp.forecast.city

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.softaai.forecastapp.R
import com.softaai.forecastapp.data.network.State
import com.softaai.forecastapp.databinding.ActivityCityWheatherInfoBinding
import com.softaai.forecastapp.forecast.loading.LoadingScreen
import com.softaai.forecastapp.forecast.error.ErrorScreenFragment
import com.softaai.forecastapp.forecast.city.fivedays.FiveDaysForecastViewModel
import com.softaai.forecastapp.forecast.city.todays.TodaysForecastViewModel
import com.softaai.forecastapp.model.fivedays.FiveDaysForecastApiResponse
import com.softaai.forecastapp.model.todays.TodaysForecastApiResponse
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class CityScreenActivity : AppCompatActivity() {

    val todaysForecastViewModel: TodaysForecastViewModel by viewModels()

    val fiveDaysForecastViewModel: FiveDaysForecastViewModel by viewModels()

    var loadingScreenAnimation: LoadingScreen? = null

    private lateinit var activityCityWheatherInfoBinding: ActivityCityWheatherInfoBinding

    private val constraintSet1 = ConstraintSet()
    private val constraintSet2 = ConstraintSet()

    private var isOffscreen = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingScreenAnimation = LoadingScreen(this)
        loadingScreenAnimation?.show()

        activityCityWheatherInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_wheather_info)

        constraintSet1.clone(activityCityWheatherInfoBinding.constraintLayoutWeatherInfo) //1
        constraintSet2.clone(this, R.layout.activity_city_wheather_info_anim_end) //2

        getTodaysForecast()
        getFiveDaysForecast()
    }


    private fun setupTodaysWeatherForecastInfo(todaysForecastApiResponse: TodaysForecastApiResponse?) {
        activityCityWheatherInfoBinding.tvCurrentTemperature.text = todaysForecastApiResponse?.main?.temp?.let { it1 -> getTempInCelcius(it1) }.toString() + "\u00B0"
        activityCityWheatherInfoBinding.tvCurrentLocation.text = todaysForecastApiResponse?.name
    }


    private fun setupFiveDaysWeatherForecastInfo(fiveDaysForecastApiResponse: FiveDaysForecastApiResponse?) {
        //activityCityWheatherInfoBinding.tvDayOne.text = fiveDaysForecastApiResponse?.list?.get(1)?.dtTxt?.let { it1 -> getDayName(it1) }
        activityCityWheatherInfoBinding.tvDayOne.text = fiveDaysForecastApiResponse?.list?.get(1)?.weather?.get(0)?.main.toString()
        activityCityWheatherInfoBinding.tvDayOneAveTemperature.text = fiveDaysForecastApiResponse?.list?.get(1)?.main?.temp?.let { it1 -> getTempInCelcius(it1) }.toString() + " C"

        //activityCityWheatherInfoBinding.tvDayTwo.text = fiveDaysForecastApiResponse?.list?.get(2)?.dtTxt?.let { it1 -> getDayName(it1) }
        activityCityWheatherInfoBinding.tvDayTwo.text = fiveDaysForecastApiResponse?.list?.get(2)?.weather?.get(0)?.main.toString()
        activityCityWheatherInfoBinding.tvDayTwoAveTemperature.text = fiveDaysForecastApiResponse?.list?.get(2)?.main?.temp?.let { it1 -> getTempInCelcius(it1) }.toString() + " C"

        //activityCityWheatherInfoBinding.tvDayThree.text = fiveDaysForecastApiResponse?.list?.get(3)?.dtTxt?.let { it1 -> getDayName(it1) }
        activityCityWheatherInfoBinding.tvDayThree.text = fiveDaysForecastApiResponse?.list?.get(2)?.weather?.get(0)?.main.toString()
        activityCityWheatherInfoBinding.tvDayThreeAveTemperature.text = fiveDaysForecastApiResponse?.list?.get(3)?.main?.temp?.let { it1 -> getTempInCelcius(it1) }.toString() + " C"

        //activityCityWheatherInfoBinding.tvDayFour.text = fiveDaysForecastApiResponse?.list?.get(4)?.dtTxt?.let { it1 -> getDayName(it1) }
        activityCityWheatherInfoBinding.tvDayFour.text = fiveDaysForecastApiResponse?.list?.get(2)?.weather?.get(0)?.main.toString()
        activityCityWheatherInfoBinding.tvDayFourAveTemperature.text = fiveDaysForecastApiResponse?.list?.get(4)?.main?.temp?.let { it1 -> getTempInCelcius(it1) }.toString() + " C"

    }


    private fun getDayName(dateString : String?) : String{

        val date = dateString?.let{ SimpleDateFormat("yyyy-MM-dd").parse(dateString) }
        val simpleDateformat = SimpleDateFormat("EEEE")
        return simpleDateformat.format(date)
    }

    private fun getTempInCelcius(it1: Double?) : Int? {
        return it1?.minus(273.15)?.toInt()
    }

    override fun onStart() {
        super.onStart()

        observeTodaysForecast()
        observeFiveDaysForecast()
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
                is State.Loading -> loadingScreenAnimation?.show()
                is State.Success -> {
                    performAnimation()
                    setupTodaysWeatherForecastInfo(state.data)
                    //Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                    loadingScreenAnimation?.dismiss()
                }
                is State.Error -> {
                    //Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT).show()
                    showErrorScreen()
                    loadingScreenAnimation?.dismiss()
                }
            }
        }
    }

    private fun observeFiveDaysForecast() {
        fiveDaysForecastViewModel.fiveDaysForecastLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> loadingScreenAnimation?.show()
                is State.Success -> {
                        performAnimation()
                        setupFiveDaysWeatherForecastInfo(state.data)
                        //Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                        loadingScreenAnimation?.dismiss()

                }
                is State.Error -> {
                    //Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT).show()
                    showErrorScreen()
                    loadingScreenAnimation?.dismiss()
                }
            }
        }
    }

    private fun showErrorScreen(){
        val errorScreenFragment = ErrorScreenFragment()
        supportFragmentManager.beginTransaction().replace(R.id.constraint_layout_weather_info, errorScreenFragment, "ErrorScreenTag").commit()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun performAnimation(){
        TransitionManager.beginDelayedTransition(activityCityWheatherInfoBinding.constraintLayoutWeatherInfo) //4
        val constraint = if (!isOffscreen) constraintSet1 else constraintSet2
        isOffscreen = !isOffscreen
        constraint.applyTo(activityCityWheatherInfoBinding.constraintLayoutWeatherInfo) //5

    }
}