package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.mappers

import ru.heatalways.amazingapplication.common.utils.painterRes
import ru.heatalways.amazingapplication.core.data.db.entities.HateTopEntity
import ru.heatalways.amazingapplication.feature.hate_top.api.domain.HateTopUnit
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.HateTopItem
import java.io.File

internal fun HateTopEntity.toDomain() = HateTopUnit(
    id = this.id,
    name = this.name,
    avatarPath = this.avatarPath,
    tapCount = this.tapCount,
)

internal fun HateTopUnit.toDAO() = HateTopEntity(
    id = this.id,
    name = this.name,
    avatarPath = this.avatarPath,
    tapCount = this.tapCount,
)

internal fun HateTopUnit.toUIListItem() = HateTopItem(
    id = this.id,
    name = this.name,
    avatar = painterRes(File(this.avatarPath)),
    tapCount = this.tapCount
)

fun List<HateTopUnit>.toUIListItem() = map(HateTopUnit::toUIListItem)