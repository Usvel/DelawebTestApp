package com.example.delawebtestapp.data.dto

data class NewsDto(
    val status: String,
    val totalResults: Int = 0,
    val articles: List<ArticleDto>
)
