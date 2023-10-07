package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api

import ru.heatalways.amazingasfuckapplication.core.navigation.api.Route

data class PidorEditScreenRoute(
    val id: Long = -1,
    val name: String = "",
    val photoPath: String = "",
) : Route