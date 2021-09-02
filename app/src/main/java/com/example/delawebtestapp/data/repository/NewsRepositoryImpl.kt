package com.example.delawebtestapp.data.repository

import com.example.delawebtestapp.data.cache.MemoryCacheNews
import com.example.delawebtestapp.domain.entitys.News
import com.example.delawebtestapp.domain.repositorys.NewsRepository
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val memoryCache: MemoryCacheNews
) : NewsRepository {

    override fun getNews(index: Int): Single<News> {
        return memoryCache.getNews(index)
    }
}