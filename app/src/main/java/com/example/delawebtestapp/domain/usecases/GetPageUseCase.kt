package com.example.delawebtestapp.domain.usecases

import com.example.delawebtestapp.domain.repositorys.PageRepository
import javax.inject.Inject

class GetPageUseCase @Inject constructor(private val pageRepository: PageRepository) {
    fun getNextPage() = pageRepository.getNextPage()
    fun getListNews() = pageRepository.getListNews()
}