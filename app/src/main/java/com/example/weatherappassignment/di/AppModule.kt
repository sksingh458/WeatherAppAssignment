package com.example.weatherappassignment.di

import android.app.Application
import android.content.Context
import com.example.weatherappassignment.MainActivity
import com.example.weatherappassignment.api.ApiService
import com.example.weatherappassignment.api.RetrofitClient



import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideApiService() : ApiService {
            return RetrofitClient.client.create(ApiService::class.java)
        }
    }
}