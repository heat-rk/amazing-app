package ru.heatalways.amazingasfuckapplication.presentation.screens.mirror

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorContract.Intent

class MirrorViewModel(
    private val router: Router
) : MviViewModel<Unit, Intent>(Unit) {
    override fun onNewIntent(intent: Intent) {
        when (intent) {
            Intent.OnNavigationButtonClick -> onNavigationButtonClick()
        }
    }

    private fun onNavigationButtonClick() {
        viewModelScope.launch {
            router.navigateBack()
        }
    }
}