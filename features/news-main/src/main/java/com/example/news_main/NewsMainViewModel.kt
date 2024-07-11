@file:Suppress("PackageName")

package com.example.news_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_data.RequestResult
import com.example.news_main.model.ArticleUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
internal class NewsMainViewModel
@Inject
constructor(
    getAllArticlesUseCase: Provider<GetAllArticlesUseCase>
) : ViewModel() {
    val state: StateFlow<State> =
        getAllArticlesUseCase
            .get()
            .invoke()
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}

private fun RequestResult<List<ArticleUI>>.toState(): State =
    when (this) {
        is RequestResult.Success -> State.Success(data)
        is RequestResult.Error -> State.Error(data)
        is RequestResult.InProgress -> State.Loading(data)
    }

internal sealed class State(
    val articles: List<ArticleUI>?
) {
    data object None : State(articles = null)

    class Loading(
        articles: List<ArticleUI>? = null
    ) : State(articles)

    class Error(
        articles: List<ArticleUI>? = null
    ) : State(articles)

    class Success(
        articles: List<ArticleUI>
    ) : State(articles)
}
