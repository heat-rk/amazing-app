package ru.heatalways.amazingapplication.feature.mirror.compose_impl.ui

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import ru.heatalways.amazingapplication.core.navigation.api.Router

class MirrorViewModel(
    private val router: Router
) : ViewModel(), ContainerHost<Unit, Unit> {

    override val container = container<Unit, Unit>(initialState = Unit)

    fun onNavigationButtonClick() = intent {
        router.navigateBack()
    }
}