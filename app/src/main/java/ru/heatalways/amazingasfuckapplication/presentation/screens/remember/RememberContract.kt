package ru.heatalways.amazingasfuckapplication.presentation.screens.remember

object RememberContract {
    sealed interface Intent {
        object OnNavigationButtonClick : Intent
    }
}