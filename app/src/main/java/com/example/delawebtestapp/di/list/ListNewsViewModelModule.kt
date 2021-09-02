package com.serma.fintechtestapp.di.hot

import androidx.lifecycle.ViewModel
import com.example.delawebtestapp.data.remote.PageRemoteSource
import com.example.delawebtestapp.data.remote.PageRemoteSourceImpl
import com.example.delawebtestapp.data.repository.PageRepositoryImp
import com.example.delawebtestapp.domain.repositorys.PageRepository
import com.example.delawebtestapp.presentation.list.ListNewsViewModel
import com.serma.fintechtestapp.di.keys.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ListNewsViewModelModule {

    @ViewModelKey(ListNewsViewModel::class)
    @IntoMap
    @Binds
    abstract fun bindsListNewsViewModule(listNewsViewModel: ListNewsViewModel): ViewModel

    @Binds
    abstract fun bindsPageRepository(pageRepositoryImpl: PageRepositoryImp): PageRepository

    @Binds
    abstract fun bindsPageRemoteSource(pageRemoteSourceImpl: PageRemoteSourceImpl): PageRemoteSource
}