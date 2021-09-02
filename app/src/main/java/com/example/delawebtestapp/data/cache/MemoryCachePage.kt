package com.example.delawebtestapp.data.cache

import com.example.delawebtestapp.domain.entitys.News
import io.reactivex.Single

interface MemoryCachePage {

    fun setNewPage(pageNews: List<News>)

    fun setTotalResults(totalResults: Int)

    fun getTotalResults(): Int

    fun getLoadedNews(): Single<List<News>>

    fun getPage(): Int
}