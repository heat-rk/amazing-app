package ru.heatalways.amazingasfuckapplication.mappers

import ru.heatalways.amazingasfuckapplication.common.utils.painterRes
import ru.heatalways.amazingasfuckapplication.core.data.db.entities.PidorEntity
import ru.heatalways.amazingasfuckapplication.feature.pidors.api.domain.Pidor
import ru.heatalways.amazingasfuckapplication.feature.pidors.impl.ui.PidorItem
import java.io.File

fun PidorEntity.toDomain() = Pidor(
    id = this.id,
    name = this.name,
    avatarPath = this.avatarPath,
    tapCount = this.tapCount,
)

fun Pidor.toDAO() = PidorEntity(
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