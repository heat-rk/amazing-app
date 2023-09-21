package ru.heatalways.amazingasfuckapplication.core.navigation.api

abstract class ScreenRoute(
    params: List<String> = emptyList(),
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

    private fun List<String>.toQueryParams() =
        joinToString(separator = "&") { "${it}={${it}}" }
}