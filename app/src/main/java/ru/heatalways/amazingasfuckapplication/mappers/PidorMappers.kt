package ru.heatalways.amazingasfuckapplication.mappers

import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO
import ru.heatalways.amazingasfuckapplication.domain.pidors.Pidor
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.PidorItem
import ru.heatalways.amazingasfuckapplication.utils.painterRes

fun PidorDAO.toDomain() = Pidor(
    id = this.id,
    name = this.name,
    avatarPath = this.avatarPath,
    tapCount = this.tapCount,
)

fun Pidor.toDAO() = PidorDAO(
    id = this.id,
    name = this.name,
    avatarPath = this.avatarPath,
    tapCount = this.tapCount,
)

fun Pidor.toUIListItem() = PidorItem(
    id = this.id,
    name = this.name,
    avatar = painterRes(this.avatarPath),
    tapCount = this.tapCount
)