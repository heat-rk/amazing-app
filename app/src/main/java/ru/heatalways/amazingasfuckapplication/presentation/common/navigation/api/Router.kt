package ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api

interface Router {
    suspend fun navigate(route: ScreenRoute)
    suspend fun navigateBack()
}