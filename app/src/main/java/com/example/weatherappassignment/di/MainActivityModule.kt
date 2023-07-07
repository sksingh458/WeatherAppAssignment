package com.example.weatherappassignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappassignment.repositry.WeatherInfoShowModel
import com.example.weatherappassignment.repositry.WeatherInfoShowModelImpl
import com.example.weatherappassignment.viewModel.WeatherInfoViewModel
import com.example.weatherappassignment.viewModel.WeatherInfoViewModelFactory

import dagger.Binds
import dagger.Module


@Module
abstract class MainActivityModule {

    @Binds
    abstract fun bindMainViewModel(viewModel: WeatherInfoViewModel): ViewModel

    @Binds
    abstract fun bindModel(weatherInfoShowModelImpl: WeatherInfoShowModelImpl): WeatherInfoShowModel

    @Binds
    abstract fun bindWeatherInfoViewModelFactory(factory: WeatherInfoViewModelFactory): ViewModelProvider.Factory
}