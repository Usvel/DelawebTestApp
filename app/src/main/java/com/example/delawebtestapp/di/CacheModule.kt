package com.example.delawebtestapp.di

import com.example.delawebtestapp.data.cache.MemoryCacheImpl
import com.example.delawebtestapp.data.cache.MemoryCacheNews
import com.example.delawebtestapp.data.cache.MemoryCachePage
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CacheModule {

    @Binds
    @Singleton
    fun bindsCachePage(memoryCacheImpl: MemoryCacheImpl): MemoryCachePage

    @Binds
    @Singleton
    fun bindsCacheNews(memoryCacheImpl: MemoryCacheImpl): MemoryCacheNews
}