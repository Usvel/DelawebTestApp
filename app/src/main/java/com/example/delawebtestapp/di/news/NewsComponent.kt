package com.example.delawebtestapp.di.news

import com.example.delawebtestapp.presentation.news.NewsFragment
import com.serma.fintechtestapp.di.scope.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [NewsViewModelModule::class])
@FragmentScope
interface NewsComponent {

    fun inject(newsFragment: NewsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsComponent
    }
}