@file:Suppress("PackageName")

package com.example.news_data

import com.example.news_data.RequestResult.Error
import com.example.news_data.RequestResult.InProgress
import com.example.news_data.RequestResult.Success

interface MergeStrategy<E> {
    fun merge(
        right: E,
        left: E
    ): E
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {
    @Suppress("CyclomaticComplexMethod")
    override fun merge(
        right: RequestResult<T>,
        left: RequestResult<T>
    ): RequestResult<T> =
        when {
            right is InProgress && left is InProgress -> merge(right, left)
            right is Success && left is InProgress -> merge(right, left)
            right is InProgress && left is Success -> merge(right, left)
            right is Success && left is Success -> merge(right, left)
            right is Success && left is Error -> merge(right, left)
            right is InProgress && left is Error -> merge(right, left)
            right is Error && left is InProgress -> merge(left)
            right is Error && left is Success -> merge(left)

            else -> error("Unimplemented branch right=$right & left=$left")
        }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: InProgress<T>
    ): RequestResult<T> = InProgress(cache.data)

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: InProgress<T>,
        server: Success<T>
    ): RequestResult<T> = InProgress(server.data)

    private fun merge(
        cache: Success<T>,
        server: Error<T>
    ): RequestResult<T> = Error(data = cache.data, error = server.error)

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: Success<T>
    ): RequestResult<T> = Success(data = server.data)

    private fun merge(
        cache: InProgress<T>,
        server: Error<T>
    ): RequestResult<T> = Error(data = server.data ?: cache.data, error = server.error)

    private fun merge(server: InProgress<T>): RequestResult<T> = server

    private fun merge(server: Success<T>): RequestResult<T> = server
}
