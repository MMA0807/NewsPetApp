package com.example.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.main.model.ArticleUI

@Composable
internal fun Articles(
    articleState: State.Success,
    modifier: Modifier = Modifier,
) {
    Articles(articles = articleState.articles, modifier)
}

@Preview
@Composable
internal fun Articles(
    @PreviewParameter(
        ArticlesPreviewProvider::class,
        limit = 1
    ) articles: List<ArticleUI>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
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
    ) article: ArticleUI,
    modifier: Modifier = Modifier
) {
    Row(modifier.padding(bottom = 4.dp)) {
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
        Spacer(modifier.size(4.dp))
        Column(modifier.padding(8.dp)) {
            val title = article.title
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1
                )
            }
            Spacer(modifier.size(4.dp))
            val description = article.description
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
            }
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
