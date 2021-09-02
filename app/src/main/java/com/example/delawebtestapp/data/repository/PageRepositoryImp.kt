package com.example.delawebtestapp.data.repository

import com.example.delawebtestapp.data.cache.MemoryCachePage
import com.example.delawebtestapp.data.mapper.NewsDtoMapper
import com.example.delawebtestapp.data.remote.PageRemoteSource
import com.example.delawebtestapp.di.RetrofitModule
import com.example.delawebtestapp.domain.entitys.News
import com.example.delawebtestapp.domain.repositorys.PageRepository
import io.reactivex.Single
import javax.inject.Inject

class PageRepositoryImp @Inject constructor(
    private val memoryCache: MemoryCachePage,
    private val pageRemoteSource: PageRemoteSource,
    private val newsDtoMapper: NewsDtoMapper
) : PageRepository {

    private fun getPageNews(page: Int): Single<List<News>> {
        return pageRemoteSource.getNews(page).map { result ->
            memoryCache.setTotalResults(result.totalResults)
            result.articles
        }.map { list ->
            list.map {
                newsDtoMapper.toEntity(it)
            }.also {
                memoryCache.setNewPage(it)
            }
        }
    }

    override fun getListNews(): Single<List<News>> {
        return when (memoryCache.getPage()) {
            0 -> getPageNews(memoryCache.getPage() + 1)
            else -> getLoadedNews()
        }
    }

    private fun getLoadedNews(): Single<List<News>> {
        return memoryCache.getLoadedNews()
    }

    override fun getNextPage(): Single<List<News>> {
        val nextPage = memoryCache.getPage() + 1
        return if (nextPage * RetrofitModule.PAGE_SIZE <= memoryCache.getTotalResults()) {
            getPageNews(memoryCache.getPage() + 1)
        } else {
            Single.just(listOf())
        }
    }
}