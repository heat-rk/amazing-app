package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppBar
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppButton
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppTextField
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.PagerScreenPaws
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRouteDefinition
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.ViewState
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.presentation.styles.Insets
import ru.heatalways.amazingasfuckapplication.utils.PainterResource
import ru.heatalways.amazingasfuckapplication.utils.extract
import ru.heatalways.amazingasfuckapplication.utils.painterRes

object PidorEditScreen {
    const val NAME_PARAM = "name"
    const val PHOTO_PATH = "photo_path"

    object RouteDefinition : ScreenRouteDefinition(
        params = listOf(
            navArgument(NAME_PARAM) {
                type = NavType.StringType
                defaultValue = ""
            },

            navArgument(PHOTO_PATH) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    )
}

@Composable
fun PidorEditScreen(
    name: String,
    photoPath: String,
    viewModel: PidorEditViewModel = koinViewModel(
        parameters = { parametersOf(name, photoPath) }
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PidorEditScreen(
        state = state,
        onIntent = viewModel::intent
    )
}

@Composable
private fun PidorEditScreen(
    state: ViewState,
    onIntent: (Intent) -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.pidor_addition_title),
                icon = painterResource(R.drawable.icon_leaderboard),
                onGoBackClick = { onIntent(Intent.OnNavigationButtonClick) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        PidorEditScreenContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun PidorEditScreenContent(
    state: ViewState,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val path = uri?.path

        if (path != null) {
            onIntent(Intent.OnAvatarPathChanged(path))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = Insets.Default,
                    end = Insets.Default,
                    top = Insets.Large
                )
        ) {
            AppTextField(
                value = state.name,
                placeholder = stringResource(R.string.pidor_addition_name_placeholder),
                label = stringResource(R.string.pidor_addition_name_label),
                onValueChange = { onIntent(Intent.OnNameChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            Spacer(modifier = Modifier.height(Insets.Default))

            AppButton(
                text = stringResource(R.string.pidor_addition_select_photo),
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            Spacer(modifier = Modifier.height(Insets.Default))

            val photoPainter = state.avatar.extract()

            Image(
                painter = photoPainter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            AppTheme.colors.background,
                            AppTheme.colors.background,
                            AppTheme.colors.background,
                        ),
                    )
                )
        ) {
            PagerScreenPaws(
                onClick = { onIntent(Intent.OnSaveClick) },
                text = stringResource(R.string.pidor_addition_save),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Insets.ExtraLarge)
            )
        }
    }
}

@Composable
@Preview
private fun PidorsScreenPreview() {
    val state = ViewState(
        name = "",
        avatar = painterRes(R.drawable.icon_boobs),
    )

    AppTheme {
        PidorEditScreen(
            state = state,
            onIntent = { /* ... */ },
        )
    }
}