package ru.heatalways.amazingasfuckapplication.feature.facts.compose_impl.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FactDTO(
    @SerialName("fact") val fact: String? = null
)
