package com.serma.fintechtestapp.di.hot

import androidx.lifecycle.ViewModel
import com.example.delawebtestapp.data.mapper.NewsDtoMapper
import com.example.delawebtestapp.data.remote.PageRemoteSource
import com.example.delawebtestapp.data.remote.PageRemoteSourceImpl
import com.example.delawebtestapp.data.repository.PageRepositoryImp
import com.example.delawebtestapp.domain.repositorys.PageRepository
import com.example.delawebtestapp.presentation.lisrt.ListNewsViewModel
import com.serma.fintechtestapp.di.keys.ViewModelKey
import com.serma.fintechtestapp.di.scope.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap

@Module
abstract class ListNewsViewModelModule {

    @ViewModelKey(ListNewsViewModel::class)
    @FragmentScope
    @IntoMap
    @Binds
    abstract fun bindsListNewsViewModule(listNewsViewModel: ListNewsViewModel): ViewModel

    @Binds
    @FragmentScope
    abstract fun bindsPageRepository(pageRepositoryImpl: PageRepositoryImp): PageRepository

    @Binds
    @FragmentScope
    abstract fun bindsPageRemoteSource(pageRemoteSourceImpl: PageRemoteSourceImpl): PageRemoteSource

    companion object {
        @Provides
        @Reusable
        fun provideMapper(): NewsDtoMapper {
            return NewsDtoMapper()
        }
    }
}