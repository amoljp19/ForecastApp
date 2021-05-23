package com.softaai.forecastapp.forecast.error

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ErrorViewModel : ViewModel() {

    val liveData: LiveData<RetryState>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<RetryState>()

    fun onClick(view: View) {
        mutableLiveData.postValue(RetryState.MainActivity())
    }

}

sealed class RetryState {
    class MainActivity : RetryState()
}