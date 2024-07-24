package com.example.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

public class AppDispatchers(
    public val default: CoroutineDispatcher = Dispatchers.Default,
    public val io: CoroutineDispatcher = Dispatchers.IO,
    public val main: CoroutineDispatcher = Dispatchers.Main,
    public val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
)
