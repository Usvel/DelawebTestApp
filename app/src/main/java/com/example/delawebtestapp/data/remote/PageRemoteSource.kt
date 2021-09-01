package com.example.delawebtestapp.data.remote

import com.example.delawebtestapp.data.dto.NewsDto
import io.reactivex.Single

interface PageRemoteSource {
    fun getNews(page: Int): Single<NewsDto>
}