package com.androidtestproject

import android.app.Application
import com.androidtestproject.di.*

class MainApplication:Application() {

    lateinit var appcomponent: Appcomponent

    override fun onCreate() {
        super.onCreate()
        appInstance=this
        appcomponent =DaggerAppcomponent.builder()
            .appModule(AppModule(appInstance))
            .dbModule(DbModule(appInstance))
            .networkModule(NetworkModule())
            .build()
    }

    companion object{
         lateinit var appInstance: MainApplication
    }
}