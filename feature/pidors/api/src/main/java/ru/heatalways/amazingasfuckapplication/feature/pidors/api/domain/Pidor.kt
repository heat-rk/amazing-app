package ru.heatalways.amazingasfuckapplication.feature.pidors.api.domain

data class Pidor(
    val id: Long,
    val name: String,
    val avatarPath: String,
    val tapCount: Int = 0,
)
