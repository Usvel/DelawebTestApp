package com.example.delawebtestapp.di

import androidx.lifecycle.ViewModelProvider
import com.serma.fintechtestapp.presentation.factory.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindsViewModelFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}