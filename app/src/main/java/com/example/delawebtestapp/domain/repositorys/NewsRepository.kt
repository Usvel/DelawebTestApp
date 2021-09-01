package com.example.delawebtestapp.domain.repositorys

import com.example.delawebtestapp.domain.entitys.News
import io.reactivex.Single

interface NewsRepository {
    fun getNews(index: Int): Single<News>
}