@file:Suppress("PackageName")

package com.example.news_main

import com.example.news_data.ArticlesRepository
import com.example.news_data.RequestResult
import com.example.news_data.map
import com.example.news_main.model.ArticleUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.news_data.model.Article as DataArticle

internal class GetAllArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {
    operator fun invoke(): Flow<RequestResult<List<ArticleUI>>> =
        repository
            .getAll()
            .map { requestResult ->
                requestResult.map { articles ->
                    articles.map { it.toUiArticles() }
                }
            }
}

private fun DataArticle.toUiArticles(): ArticleUI =
    ArticleUI(
        id = cacheId,
        title = title,
        description = description,
        imageUrl = urlToImage,
        url = url
    )
