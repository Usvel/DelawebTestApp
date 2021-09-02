package com.example.delawebtestapp.data.dto

import java.util.Date

data class ArticleDto(
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: Date? = null,
    val content: String? = null
)