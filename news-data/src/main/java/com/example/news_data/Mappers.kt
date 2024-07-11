@file:Suppress("PackageName")

package com.example.news_data

import com.example.database.models.ArticleDBO
import com.example.news_data.model.Article
import com.example.newsapi.models.ArticleDTO

internal fun ArticleDBO.toArticle(): Article {
    TODO()
}

internal fun ArticleDTO.toArticle(): Article {
    TODO("Not yet implemented")
}

internal fun ArticleDTO.toArticleDbo(): ArticleDBO {
    TODO("Not yet implemented")
}
