package com.example.delawebtestapp.di.news

import androidx.lifecycle.ViewModel
import com.example.delawebtestapp.data.repository.NewsRepositoryImpl
import com.example.delawebtestapp.domain.repositorys.NewsRepository
import com.example.delawebtestapp.presentation.news.NewsViewModel
import com.serma.fintechtestapp.di.keys.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewsViewModelModule {

    @ViewModelKey(NewsViewModel::class)
    @IntoMap
    @Binds
    abstract fun bindsNewsViewModule(newsViewModel: NewsViewModel): ViewModel

    @Binds
    abstract fun bindsNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}