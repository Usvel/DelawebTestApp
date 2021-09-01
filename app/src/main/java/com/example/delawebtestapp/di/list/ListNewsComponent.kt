package com.serma.fintechtestapp.di.hot

import com.example.delawebtestapp.presentation.lisrt.ListNewsFragment
import com.serma.fintechtestapp.di.scope.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [ListNewsViewModelModule::class])
@FragmentScope
interface ListNewsComponent {

    fun inject(listNewsFragment: ListNewsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ListNewsComponent
    }
}