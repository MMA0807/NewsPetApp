package com.example.news_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.news_main.model.ArticleUI

@Composable
fun NewsMainScreen() {
    NewsMainScreen(viewModel = viewModel())
}

@Composable
internal fun NewsMainScreen(viewModel: NewsMainViewModel) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    if (currentState != State.None) {
        NewsMainContent(currentState)
    }
}

@Composable
private fun NewsMainContent(currentState: State) {
    Column {
        if (currentState is State.Error) {
            ErrorMessage()
        }
        if (currentState is State.Loading) {
            ProgressIndicator()
        }
        if (currentState.articles != null) {
            Articles(articles = currentState.articles)
        }
    }
}

@Composable
internal fun ErrorMessage() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error", color = MaterialTheme.colorScheme.onError)
    }
}

@Composable
internal fun ProgressIndicator() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
internal fun NewsEmpty() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "No NEWS")
    }
}

@Composable
internal fun Articles(
    @PreviewParameter(
        ArticlesPreviewProvider::class,
        limit = 1
    ) articles: List<ArticleUI>
) {
    LazyColumn {
        items(articles) { article ->
            key(article.id) {
                Article(article)
            }
        }
    }
}

@Preview
@Composable
internal fun Article(
    @PreviewParameter(
        ArticlePreviewProvider::class,
        limit = 1
    ) article: ArticleUI
) {
    Row(Modifier.padding(bottom = 4.dp)) {
        article.imageUrl?.let { imageUrl ->
            var isImageVisible by remember { mutableStateOf(true) }
            if (isImageVisible) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "",
                    onState = { state ->
                        if (state is AsyncImagePainter.State.Error) isImageVisible = false
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = article.description,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 3
            )
        }
    }
}

@Suppress("MagicNumber")
private class ArticlePreviewProvider : PreviewParameterProvider<ArticleUI> {
    override val values: Sequence<ArticleUI>
        get() =
            sequenceOf(
                ArticleUI(
                    1,
                    "Lorem Ipsum is simply!",
                    "dummy text of the printing and typesetting industry. ",
                    null,
                    ""
                ),
                ArticleUI(
                    2,
                    "Why do we use it",
                    "It is a long established fact that a reader will be",
                    null,
                    ""
                ),
                ArticleUI(
                    3,
                    "Where does it come from?",
                    "default model text, and a search for 'lorem ipsum",
                    null,
                    ""
                ),
                ArticleUI(
                    4,
                    "Where can I get some?",
                    "dummy text of the printing and typesetting industry. ",
                    null,
                    ""
                )
            )
}

private class ArticlesPreviewProvider : PreviewParameterProvider<List<ArticleUI>> {
    private val articleProvider = ArticlePreviewProvider()

    override val values = sequenceOf(articleProvider.values.toList())
}
