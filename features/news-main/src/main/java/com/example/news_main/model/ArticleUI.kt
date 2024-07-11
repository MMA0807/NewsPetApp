package com.example.news_main.model

data class ArticleUI(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val url: String
)
