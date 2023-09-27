package ru.heatalways.amazingasfuckapplication.feature.cats.impl.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDTO(
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("file") val file: String? = null,
    @SerialName("_id") val id: String? = null,
    @SerialName("mimetype") val mimetype: String? = null,
    @SerialName("owner") val owner: String? = null,
    @SerialName("tags") val tags: List<String?>? = null,
    @SerialName("updatedAt") val updatedAt: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("validated") val validated: Boolean? = null
)