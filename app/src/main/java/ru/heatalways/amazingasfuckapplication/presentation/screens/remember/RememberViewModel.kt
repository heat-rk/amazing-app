package ru.heatalways.amazingasfuckapplication.presentation.screens.remember

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.heatalways.amazingasfuckapplication.presentation.common.mvi.MviViewModel
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.Router
import ru.heatalways.amazingasfuckapplication.presentation.screens.remember.RememberContract.Intent

class RememberViewModel(
    private val router: Router,
) : MviViewModel<Unit, Intent>(
    initialState = Unit
) {

    override fun onNewIntent(intent: Intent) {
        when (intent) {
            Intent.OnNavigationButtonClick -> navigateBack()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            router.navigateBack()
        }
    }
}