package com.example.delawebtestapp.data.cache

import com.example.delawebtestapp.domain.entitys.News
import io.reactivex.Single

interface MemoryCacheNews {

    fun getNews(index: Int) : Single<News>
}