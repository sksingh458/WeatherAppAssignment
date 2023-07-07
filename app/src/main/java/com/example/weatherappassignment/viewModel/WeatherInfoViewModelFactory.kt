package com.example.weatherappassignment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappassignment.repositry.WeatherInfoShowModel
import javax.inject.Inject

class WeatherInfoViewModelFactory @Inject constructor(private val arg: WeatherInfoShowModel) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(WeatherInfoShowModel::class.java)
            .newInstance(arg)
    }
}