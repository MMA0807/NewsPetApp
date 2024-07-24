package com.example.main

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestResult
import com.example.main.model.ArticleUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
public class NewsMainViewModel @Inject internal constructor(
    getAllArticlesUseCase: Provider<GetAllArticlesUseCase>
) : ViewModel() {
    public val state: StateFlow<State> =
        getAllArticlesUseCase
            .get()
            .invoke(query = "android")
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}

private fun RequestResult<List<ArticleUI>>.toState(): State =
    when (this) {
        is RequestResult.Success -> State.Success(data.toImmutableList())
        is RequestResult.Error -> State.Error(data?.toImmutableList())
        is RequestResult.InProgress -> State.Loading(data?.toImmutableList())
    }

@Stable
public sealed class State(public open val articles: ImmutableList<ArticleUI>?) {
    @Immutable
    public data object None : State(articles = null)

    @Stable
    public class Loading(articles: ImmutableList<ArticleUI>? = null) : State(articles)

    @Stable
    public class Error(articles: ImmutableList<ArticleUI>? = null) : State(articles)

    @Stable
    public class Success(override val articles: ImmutableList<ArticleUI>) : State(articles)
}
