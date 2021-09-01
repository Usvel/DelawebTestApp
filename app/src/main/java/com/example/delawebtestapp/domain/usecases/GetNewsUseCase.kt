package com.example.delawebtestapp.domain.usecases

import com.example.delawebtestapp.domain.repositorys.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    fun getNews(index: Int) = newsRepository.getNews(index)
}