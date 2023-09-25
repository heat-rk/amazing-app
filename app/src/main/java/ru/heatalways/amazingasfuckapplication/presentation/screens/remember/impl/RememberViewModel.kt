package ru.heatalways.amazingasfuckapplication.presentation.screens.remember.impl

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router

class RememberViewModel(
    private val router: Router,
) : ViewModel(), ContainerHost<Unit, Unit> {

    override val container = container<Unit, Unit>(initialState = Unit)

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }
}