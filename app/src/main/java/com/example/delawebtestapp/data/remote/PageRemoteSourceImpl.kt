package com.example.delawebtestapp.data.remote

import com.example.delawebtestapp.data.NewsApi
import com.example.delawebtestapp.data.dto.NewsDto
import io.reactivex.Single
import javax.inject.Inject

class PageRemoteSourceImpl @Inject constructor(
    private val newsapi: NewsApi
) : PageRemoteSource {

    override fun getNews(page: Int): Single<NewsDto> {
        return newsapi.getListNews(page)
    }
}