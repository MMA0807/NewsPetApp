package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.database.models.ArticleDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAll(): List<ArticleDBO>

    @Query("SELECT * FROM articles")
    fun observeAll(): Flow<List<ArticleDBO>>

//    @Query("DElETE FROM articles")
//    fun clean(articles: List<ArticleDBO>)

    @Insert
    fun insert(articles: List<ArticleDBO>)

    @Delete
    fun remove(articles: List<ArticleDBO>)
}
