package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.api

interface PidorsScreenNavigator {
    suspend fun navigateToList()

    suspend fun navigateToEdition(
        id: Long = -1,
        name: String = "",
        photoPath: String = ""
    )
}