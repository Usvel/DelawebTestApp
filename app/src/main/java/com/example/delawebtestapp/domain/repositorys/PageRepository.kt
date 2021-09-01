package com.example.delawebtestapp.domain.repositorys

import com.example.delawebtestapp.domain.entitys.News
import io.reactivex.Single

interface PageRepository {
    fun getNextPage(): Single<List<News>>
    fun getListNews(): Single<List<News>>
}