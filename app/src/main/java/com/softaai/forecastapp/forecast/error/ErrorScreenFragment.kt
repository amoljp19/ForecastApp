package com.softaai.forecastapp.forecast.error

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.softaai.forecastapp.R
import com.softaai.forecastapp.databinding.ErrorScreenBinding
import com.softaai.forecastapp.forecast.loading.LoadingScreen
import com.softaai.forecastapp.forecast.city.CityScreenActivity

class ErrorScreenFragment :  Fragment() {

    private var errorViewModel: ErrorViewModel? = null

    lateinit var binding: ErrorScreenBinding
    var loadingScreenAnimation: LoadingScreen? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<ErrorScreenBinding>(inflater, R.layout.error_screen, container, false)

        errorViewModel = ViewModelProviders.of(this).get(ErrorViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.setErrorViewModel(errorViewModel)

        loadingScreenAnimation = LoadingScreen(this.requireContext())
        //  loadingScreenAnimation?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorViewModel?.liveData?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RetryState.MainActivity -> {
                    val intent = Intent(activity, CityScreenActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}