package ru.heatalways.amazingapplication.feature.hate_top.api.domain

data class HateTopUnit(
    val id: Long,
    val name: String,
    val avatarPath: String,
    val tapCount: Int = 0,
)
