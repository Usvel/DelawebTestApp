package com.example.delawebtestapp

import android.app.Application
import com.example.delawebtestapp.di.AppComponent
import com.example.delawebtestapp.di.DaggerAppComponent

class App : Application() {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        return appComponent ?: DaggerAppComponent.factory().create(applicationContext).also {
            appComponent = it
        }
    }
}