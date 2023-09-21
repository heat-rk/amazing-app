package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api

import androidx.navigation.NamedNavArgument

abstract class ScreenRoute(
    val params: List<NamedNavArgument> = emptyList(),
) {
    private val definitionBase = this::class.java.name
    val definition = "$definitionBase?${params.toQueryParams()}"

    fun withArgs(args: Map<String, String> = emptyMap()) =
        buildString {
            append(definitionBase)

            if (args.isNotEmpty()) {
                append('?')
                append(args.toQueryArgs())
            }
        }

    private fun Map<String, String>.toQueryArgs() =
        entries.joinToString(separator = "&") { "${it.key}=${it.value}" }

    private fun List<NamedNavArgument>.toQueryParams() =
        joinToString(separator = "&") { "${it.name}={${it.name}}" }
}