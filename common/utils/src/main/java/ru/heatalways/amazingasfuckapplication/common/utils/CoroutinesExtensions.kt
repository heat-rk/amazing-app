package ru.heatalways.amazingasfuckapplication.common.utils

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun CoroutineScope.launchSafe(
    tag: String = "launchSafe",
    crossinline block: suspend CoroutineScope.() -> Unit,
    crossinline onError: suspend (Throwable) -> Unit,
): Job {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        launch {
            Log.e(tag, throwable.stackTraceToString())
            onError(throwable)
        }
    }

    return launch(context = exceptionHandler) {
        block()
    }
}