package ru.heatalways.amazingasfuckapplication.data.facts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FactDTO(
    @SerialName("fact") val fact: String? = null
)
