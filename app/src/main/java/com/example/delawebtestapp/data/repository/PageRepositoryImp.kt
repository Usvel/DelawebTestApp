package com.example.delawebtestapp.data.repository

import com.example.delawebtestapp.data.cache.MemoryCache
import com.example.delawebtestapp.data.mapper.NewsDtoMapper
import com.example.delawebtestapp.data.remote.PageRemoteSource
import com.example.delawebtestapp.di.RetrofitModule
import com.example.delawebtestapp.domain.entitys.News
import com.example.delawebtestapp.domain.repositorys.PageRepository
import io.reactivex.Single
import javax.inject.Inject

class PageRepositoryImp @Inject constructor(
    private val memoryCashe: MemoryCache,
    private val pageRemoteSource: PageRemoteSource,
    private val newsDtoMapper: NewsDtoMapper
) : PageRepository {

    private fun getPageNews(page: Int): Single<List<News>> {
        return pageRemoteSource.getNews(page).map { result ->
            memoryCashe.setTotalResults(result.totalResults)
            result.articles
        }.map { list ->
            list.map {
                newsDtoMapper.toEntity(it)
            }.also {
                memoryCashe.setNewPage(it)
            }
        }
    }

    override fun getListNews(): Single<List<News>> {
        return when (memoryCashe.getPage()) {
            0 -> getPageNews(memoryCashe.getPage() + 1)
            else -> getLoadedNews()
        }
    }

    private fun getLoadedNews(): Single<List<News>> {
        return memoryCashe.getLoadedNews()
    }

    override fun getNextPage(): Single<List<News>> {
        val nextPage = memoryCashe.getPage() + 1
        return if (nextPage * RetrofitModule.PAGE_SIZE <= memoryCashe.getTotalResults()) {
            getPageNews(memoryCashe.getPage() + 1)
        } else {
            Single.just(listOf())
        }
    }
}