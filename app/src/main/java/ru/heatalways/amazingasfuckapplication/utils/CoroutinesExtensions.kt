package ru.heatalways.amazingasfuckapplication.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun CoroutineScope.launchSafe(
    crossinline block: suspend CoroutineScope.() -> Unit,
    crossinline onError: suspend (Throwable) -> Unit
): Job {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        launch {
            onError(throwable)
        }
    }

    return launch(context = exceptionHandler) {
        block()
    }
}