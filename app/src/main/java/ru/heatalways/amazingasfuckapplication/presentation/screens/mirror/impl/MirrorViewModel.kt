package ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.impl

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingasfuckapplication.core.navigation.api.Router

class MirrorViewModel(
    private val router: Router
) : ViewModel(), ContainerHost<Unit, Unit> {

    override val container = container<Unit, Unit>(initialState = Unit)

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }
}