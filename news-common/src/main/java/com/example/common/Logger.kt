package com.example.common

import android.util.Log

public interface Logger {
    public fun d(
        tag: String,
        message: String
    )

    public fun e(
        tag: String,
        message: String
    )
}

public fun androidLogcatLogger(): Logger =
    object : Logger {
        override fun d(
            tag: String,
            message: String
        ) {
            Log.d(tag, message)
        }

        override fun e(
            tag: String,
            message: String
        ) {
            Log.e(tag, message)
        }
    }
