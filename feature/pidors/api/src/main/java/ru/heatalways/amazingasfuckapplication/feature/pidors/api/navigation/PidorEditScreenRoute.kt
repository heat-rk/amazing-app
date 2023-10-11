package ru.heatalways.amazingasfuckapplication.feature.pidors.api.navigation

import ru.heatalways.amazingasfuckapplication.core.navigation.api.Route

data class PidorEditScreenRoute(
    val id: Long = -1,
    val name: String = "",
    val photoPath: String = "",
) : Route