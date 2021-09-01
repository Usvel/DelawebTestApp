package com.example.delawebtestapp.data.cache

import com.example.delawebtestapp.domain.entitys.News
import io.reactivex.Single
import java.util.ArrayList
import javax.inject.Inject

class MemoryCacheImpl @Inject constructor() : MemoryCache {

    private val allLoadNews: ArrayList<News> = ArrayList()
    private var totalResults: Int = 0
    private var currentPage: Int = 0

    override fun getNews(index: Int): Single<News> {
        return Single.just(allLoadNews[index])
    }

    override fun getLoadedNews(): Single<List<News>> {
        return Single.just(allLoadNews)
    }

    override fun getPage(): Int = currentPage

    override fun setTotalResults(totalResults: Int) {
        this.totalResults = totalResults
    }

    override fun getTotalResults() = totalResults

    override fun setNewPage(pageNews: List<News>) {
        currentPage++
        allLoadNews.addAll(pageNews)
    }
}