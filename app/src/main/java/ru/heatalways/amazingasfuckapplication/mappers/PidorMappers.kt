package ru.heatalways.amazingasfuckapplication.mappers

import ru.heatalways.amazingasfuckapplication.common.utils.painterRes
import ru.heatalways.amazingasfuckapplication.data.pidors.database.PidorDAO
import ru.heatalways.amazingasfuckapplication.domain.pidors.Pidor
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.impl.PidorItem
import java.io.File

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
    avatar = painterRes(File(this.avatarPath)),
    tapCount = this.tapCount
)

fun List<Pidor>.toUIListItem() = map(Pidor::toUIListItem)