package ru.heatalways.amazingasfuckapplication.domain.pidors

sealed interface PidorAvatarCrop {
    object Full : PidorAvatarCrop

    data class Exactly(
        val left: Int,
        val top: Int,
        val width: Int,
        val height: Int
    ) : PidorAvatarCrop
}