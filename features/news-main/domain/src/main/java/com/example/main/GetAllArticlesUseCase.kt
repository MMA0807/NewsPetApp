package com.example.main

import com.example.data.ArticlesRepository
import com.example.data.RequestResult
import com.example.data.map
import com.example.data.model.Article
import com.example.main.model.ArticleUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetAllArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {
    operator fun invoke(query: String): Flow<RequestResult<List<ArticleUI>>> =
        repository
            .getAll(query)
            .map { requestResult ->
                requestResult.map { articles ->
                    articles.map { it.toUiArticle() }
                }
            }
}

private fun Article.toUiArticle(): ArticleUI =
    ArticleUI(
        id = cacheId,
        title = title,
        description = description,
        imageUrl = urlToImage,
        url = url
    )
