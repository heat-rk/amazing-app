package ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentScope
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.common.utils.extract
import ru.heatalways.amazingasfuckapplication.common.utils.painterRes
import ru.heatalways.amazingasfuckapplication.core.design.composables.AppBar
import ru.heatalways.amazingasfuckapplication.core.design.composables.AppButton
import ru.heatalways.amazingasfuckapplication.core.design.composables.AppSnackbarHost
import ru.heatalways.amazingasfuckapplication.core.design.composables.AppTextField
import ru.heatalways.amazingasfuckapplication.core.design.styles.AppTheme
import ru.heatalways.amazingasfuckapplication.core.design.styles.Insets
import ru.heatalways.amazingasfuckapplication.core.design.styles.Sizes
import ru.heatalways.amazingasfuckapplication.core.navigation.compose_impl.ComposeScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.PagerScreenPaws
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.RectangleImageCropper
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.SideEffect
import ru.heatalways.amazingasfuckapplication.presentation.screens.pidors.edit.PidorEditContract.ViewState
import ru.heatalways.amazingasfuckapplication.core.design.R as DesignR

object PidorEditScreen {
    const val ID_PARAM = "id"
    const val NAME_PARAM = "name"
    const val PHOTO_PATH = "photo_path"

    object Route : ComposeScreenRoute(
        namedNavArguments = listOf(
            navArgument(ID_PARAM) {
                type = NavType.LongType
                defaultValue = -1L
            },

            navArgument(NAME_PARAM) {
                type = NavType.StringType
                defaultValue = ""
            },

            navArgument(PHOTO_PATH) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        @Composable
        override fun AnimatedContentScope.Content(navBackStackEntry: NavBackStackEntry) {
            PidorEditScreen(
                id = navBackStackEntry.arguments?.getLong(ID_PARAM) ?: -1,
                name = navBackStackEntry.arguments?.getString(NAME_PARAM) ?: "",
                photoPath = navBackStackEntry.arguments?.getString(PHOTO_PATH) ?: "",
            )
        }

    }
}

@Composable
fun PidorEditScreen(
    id: Long,
    name: String,
    photoPath: String,
    viewModel: PidorEditViewModel = koinViewModel(
        parameters = { parametersOf(id, name, photoPath) }
    ),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val sideEffects = viewModel.container.sideEffectFlow
    val snackbarHostState = remember { SnackbarHostState() }

    PidorsScreenSideEffect(
        sideEffects = sideEffects,
        snackbarHostState = snackbarHostState,
    )

    PidorEditScreen(
        state = state,
        onNavigationButtonClick = viewModel::onNavigationButtonClick,
        onAvatarChanged = viewModel::onAvatarChanged,
        onAvatarCropChanged = viewModel::onAvatarCropChanged,
        onNameChanged = viewModel::onNameChange,
        onSaveClick = viewModel::onSaveClick,
        snackbarHostState = snackbarHostState,
    )
}

@Composable
private fun PidorEditScreen(
    state: ViewState,
    snackbarHostState: SnackbarHostState,
    onNavigationButtonClick: () -> Unit,
    onAvatarChanged: (Uri) -> Unit,
    onAvatarCropChanged: (IntOffset, IntSize) -> Unit,
    onNameChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            AppBar(
                title = if (state.isEdition) {
                    stringResource(R.string.pidor_edition_title)
                } else {
                    stringResource(R.string.pidor_addition_title)
                },
                icon = painterResource(DesignR.drawable.icon_leaderboard),
                onGoBackClick = onNavigationButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        PidorEditScreenContent(
            state = state,
            onAvatarChanged = onAvatarChanged,
            onAvatarCropChanged = onAvatarCropChanged,
            onNameChanged = onNameChanged,
            onSaveClick = onSaveClick,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun PidorEditScreenContent(
    state: ViewState,
    onAvatarChanged: (Uri) -> Unit,
    onAvatarCropChanged: (IntOffset, IntSize) -> Unit,
    onNameChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onAvatarChanged(uri)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
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
            onValueChange = onNameChanged,
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

        RectangleImageCropper(
            painter = photoPainter,
            croppingBoxStrokeWidth = Sizes.PidorAvatarCroppingBoxStrokeWidth,
            onCropChanged = onAvatarCropChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(Insets.Default))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
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
                onClick = onSaveClick,
                isEnabled = state.canBeSaved,
                text = stringResource(R.string.pidor_addition_save),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Insets.ExtraLarge)
            )
        }
    }
}

@Composable
private fun PidorsScreenSideEffect(
    sideEffects: Flow<SideEffect>,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current

    LaunchedEffect(sideEffects, context) {
        sideEffects
            .onEach { sideEffect ->
                when (sideEffect) {
                    is SideEffect.Message -> {
                        handleMessageSideEffect(
                            sideEffect = sideEffect,
                            snackbarHostState = snackbarHostState,
                            context = context,
                        )
                    }
                }
            }
            .launchIn(this)
    }
}

private suspend fun handleMessageSideEffect(
    sideEffect: SideEffect.Message,
    snackbarHostState: SnackbarHostState,
    context: Context,
) {
    val message = sideEffect.message.extract(context)
        ?: return

    snackbarHostState.showSnackbar(message)
}

@Composable
@Preview
private fun PidorsScreenPreview() {
    val state = ViewState(
        name = "",
        avatar = painterRes(DesignR.drawable.icon_boobs),
        isEdition = false
    )

    AppTheme {
        PidorEditScreen(
            state = state,
            onNavigationButtonClick = {},
            onSaveClick = {},
            onNameChanged = {},
            onAvatarChanged = {},
            onAvatarCropChanged = { _, _ -> },
            snackbarHostState = SnackbarHostState(),
        )
    }
}