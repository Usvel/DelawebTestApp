package com.example.delawebtestapp.di

import android.content.Context
import com.example.delawebtestapp.di.news.NewsComponent
import com.serma.fintechtestapp.di.hot.ListNewsComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class, CacheModule::class, ViewModelFactoryModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun registerListNewsComponent(): ListNewsComponent.Factory

    fun registerNewsComponent(): NewsComponent.Factory
}