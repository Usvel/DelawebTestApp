package com.example.delawebtestapp.data

import com.example.delawebtestapp.data.dto.NewsDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything?q=russia&language=ru&sortBy=publishedAt")
    fun getListNews(@Query("page") page: Int): Single<NewsDto>
}