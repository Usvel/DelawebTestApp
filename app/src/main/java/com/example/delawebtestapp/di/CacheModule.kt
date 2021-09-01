package com.example.delawebtestapp.di

import com.example.delawebtestapp.data.cache.MemoryCache
import com.example.delawebtestapp.data.cache.MemoryCacheImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CacheModule {

    @Binds
    @Singleton
    fun bindsCachePage(memoryCacheImpl: MemoryCacheImpl): MemoryCache
}