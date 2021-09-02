package com.example.delawebtestapp.data.mapper

import com.example.delawebtestapp.data.dto.ArticleDto
import com.example.delawebtestapp.domain.entitys.News
import javax.inject.Inject

class NewsDtoMapper @Inject constructor() {

    fun toEntity(articleDto: ArticleDto): News {
        return News(
            title = articleDto.title ?: "",
            description = articleDto.description ?: "",
            url = articleDto.url ?: "",
            urlImage = articleDto.urlToImage ?: "",
            content = articleDto.content ?: ""
        )
    }
}