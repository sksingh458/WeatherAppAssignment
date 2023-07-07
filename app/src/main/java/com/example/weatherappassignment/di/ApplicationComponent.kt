package com.example.weatherappassignment.di

import android.app.Application
import com.example.weatherappassignment.WeatherApplication
import com.example.weatherappassignment.viewModel.WeatherInfoViewModel

import dagger.BindsInstance
import dagger.Component

import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class]
)
interface ApplicationComponent : AndroidInjector<WeatherApplication> {

    fun inject(weatherInfoViewModel: WeatherInfoViewModel)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}