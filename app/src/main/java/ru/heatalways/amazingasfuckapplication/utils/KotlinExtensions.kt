package ru.heatalways.amazingasfuckapplication.utils

inline fun <reified T> Any.ifInstance(block: (T) -> Unit) =
    (this as? T)?.let(block)