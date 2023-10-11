package ru.heatalways.amazingasfuckapplication.feature.insults.compose_impl.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsultDTO(
    @SerialName("number") val number: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("insult") val insult: String? = null,
    @SerialName("created") val created: String? = null,
    @SerialName("shown") val shown: String? = null,
    @SerialName("createdby") val createdBy: String? = null,
    @SerialName("active") val active: String? = null,
    @SerialName("comment") val comment: String? = null,
)
