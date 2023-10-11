package ru.heatalways.amazingapplication.feature.hate_top.api.domain

sealed interface HateTopUnitAvatarCrop {
    object Full : HateTopUnitAvatarCrop

    data class Exactly(
        val left: Int,
        val top: Int,
        val width: Int,
        val height: Int
    ) : HateTopUnitAvatarCrop
}