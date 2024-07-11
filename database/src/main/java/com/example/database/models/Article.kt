package com.example.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "articles")
data class ArticleDBO(
    @Embedded(prefix = "source-") val source: Source,
    @ColumnInfo("author") val author: String? = null,
    @ColumnInfo("title") val title: String? = null,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("url") val url: String? = null,
    @ColumnInfo("urlToImage") val urlToImage: String? = null,
    @ColumnInfo("publishedAt") val publishedAt: Date,
    @ColumnInfo("content") val content: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Long
)

data class Source(
    @ColumnInfo("id") val id: String,
    @ColumnInfo("name") val name: String
)
