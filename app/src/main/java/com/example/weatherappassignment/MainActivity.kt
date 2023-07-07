package com.example.weatherappassignment


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherappassignment.databinding.ActivityMainBinding
import com.example.weatherappassignment.model.City
import com.example.weatherappassignment.model.WeatherData
import com.example.weatherappassignment.utils.Constants
import com.example.weatherappassignment.utils.convertToListOfCityName
import com.example.weatherappassignment.viewModel.WeatherInfoViewModel
import com.example.weatherappassignment.viewModel.WeatherInfoViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: WeatherInfoViewModelFactory

    private lateinit var viewModel: WeatherInfoViewModel

    private var cityList: MutableList<City> = mutableListOf()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize ViewModel
        viewModel = ViewModelProvider(this, factory).get(WeatherInfoViewModel::class.java)


        binding.layoutInput.apply {
            val selectedCityId = cityList[spinner.selectedItemPosition].id
            sharePrefs(selectedCityId)
        }

        getWeatherInfo()
        // set LiveData and View click listeners before the call for data fetching
        setLiveDataListeners()
        setViewClickListener()

        viewModel.getCityList()
    }

    private fun getWeatherInfo() {
        viewModel.getWeatherInfo(getPref())
    }

    private fun setViewClickListener() {
        // View Weather button click listener
        binding.layoutInput.apply {
            btnViewWeather.setOnClickListener {
                val selectedCityId = cityList[spinner.selectedItemPosition].id
                viewModel.getWeatherInfo(selectedCityId) // fetch weather info
            }
        }
    }

    private fun setLiveDataListeners() {

        viewModel.cityListLiveData.observe(this, object : Observer<MutableList<City>> {
            override fun onChanged(cities: MutableList<City>) {
                setCityListSpinner(cities)
            }
        })


        viewModel.cityListFailureLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })


        viewModel.progressBarLiveData.observe(this, Observer { isShowLoader ->
            if (isShowLoader)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })

        viewModel.weatherInfoLiveData.observe(this, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })

        viewModel.weatherInfoFailureLiveData.observe(this, Observer { errorMessage ->
            binding.apply {
                outputGroup.visibility = View.GONE
                tvErrorMessage.visibility = View.VISIBLE
                tvErrorMessage.text = errorMessage
            }
        })
    }

    private fun setCityListSpinner(cityList: MutableList<City>) {
        this.cityList = cityList

        val arrayAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            this.cityList.convertToListOfCityName()
        )

        binding.layoutInput.spinner.adapter = arrayAdapter
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        binding.apply {
            outputGroup.visibility = View.VISIBLE
            tvErrorMessage.visibility = View.GONE

            layoutWeatherBasic.apply {
                tvDateTime.text = weatherData.dateTime
                tvTemperature.text = weatherData.temperature
                tvCityCountry.text = weatherData.cityAndCountry
                Glide.with(this@MainActivity).load(weatherData.weatherConditionIconUrl).into(ivWeatherCondition)
                tvWeatherCondition.text = weatherData.weatherConditionIconDescription
            }

            layoutWeatherAdditional.apply {
                tvHumidityValue.text = weatherData.humidity
                tvPressureValue.text = weatherData.pressure
                tvVisibilityValue.text = weatherData.visibility
            }

            layoutSunsetSunrise.apply {
                tvSunriseTime.text = weatherData.sunrise
                tvSunsetTime.text = weatherData.sunset
            }

        }
    }

    private fun sharePrefs(selectedCityId:Int){
        val sharedPreference =  getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putInt(Constants.CITY_ID, selectedCityId)
        editor.commit()
    }

    private fun getPref():Int{
        val sharedPreference =  getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE)
        return  sharedPreference.getInt(Constants.CITY_ID,1275004)
    }
}
