package ru.heatalways.amazingasfuckapplication.domain.pidors

data class Pidor(
    val id: Long,
    val name: String,
    val avatarPath: String,
    val tapCount: Int = 0,
)
