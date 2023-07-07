package com.example.weatherappassignment.repositry

import com.example.weatherappassignment.model.City
import com.example.weatherappassignment.model.WeatherResponse


interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherResponse>)
}