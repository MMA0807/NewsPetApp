package com.example.newsapp

import android.content.Context
import com.example.database.NewsDatabase
import com.example.news_common.AndroidLogcatLogger
import com.example.news_common.AppDispatchers
import com.example.news_common.Logger
import com.example.newsapi.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi =
        NewsApi(
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
            apikey = BuildConfig.NEWS_API_KEY
        )

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase = NewsDatabase(context)

    @Provides
    @Singleton
    fun provideAppCoroutineDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    fun provideLogger(): Logger = AndroidLogcatLogger()
}
