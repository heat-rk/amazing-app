package ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.amazingapplication.common.utils.extract
import ru.heatalways.amazingapplication.common.utils.painterRes
import ru.heatalways.amazingapplication.core.composables.image_cropper.RectangleImageCropper
import ru.heatalways.amazingapplication.core.design.composables.AppBar
import ru.heatalways.amazingapplication.core.design.composables.AppButton
import ru.heatalways.amazingapplication.core.design.composables.AppSnackbarHost
import ru.heatalways.amazingapplication.core.design.composables.AppTextField
import ru.heatalways.amazingapplication.core.design.composables.PagerScreenPaws
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Insets
import ru.heatalways.amazingapplication.core.design.styles.Sizes
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.R
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.di.HateTopComponent
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit.HateTopUnitEditContract.SideEffect
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.ui.edit.HateTopUnitEditContract.ViewState
import ru.heatalways.amazingapplication.core.design.R as DesignR

@Composable
fun HateTopUnitEditScreen(
    id: Long,
    name: String,
    photoPath: String,
    viewModel: HateTopUnitEditViewModel = viewModel(
        factory = HateTopComponent.hateTopUnitEditViewModelFactory,
        extras = MutableCreationExtras().apply {
            set(HateTopUnitEditViewModel.IdKey, id)
            set(HateTopUnitEditViewModel.NameKey, name)
            set(HateTopUnitEditViewModel.PhotoPathKey, photoPath)
        }
    ),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val sideEffects = viewModel.container.sideEffectFlow
    val snackbarHostState = remember { SnackbarHostState() }

    HateTopUnitScreenSideEffect(
        sideEffects = sideEffects,
        snackbarHostState = snackbarHostState,
    )

    HateTopUnitEditScreen(
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
private fun HateTopUnitEditScreen(
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
                    stringResource(R.string.hate_top_edition_title)
                } else {
                    stringResource(R.string.hate_top_addition_title)
                },
                icon = painterResource(DesignR.drawable.icon_leaderboard),
                onGoBackClick = onNavigationButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        HateTopUnitEditScreenContent(
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
private fun HateTopUnitEditScreenContent(
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
            placeholder = stringResource(R.string.hate_top_addition_name_placeholder),
            label = stringResource(R.string.hate_top_addition_name_label),
            onValueChange = onNameChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Spacer(modifier = Modifier.height(Insets.Default))

        AppButton(
            text = stringResource(R.string.hate_top_addition_select_photo),
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
            croppingBoxStrokeWidth = Sizes.HateTopUnitAvatarCroppingBoxStrokeWidth,
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
                text = stringResource(R.string.hate_top_addition_save),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Insets.ExtraLarge)
            )
        }
    }
}

@Composable
private fun HateTopUnitScreenSideEffect(
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
private fun HateTopUnitScreenPreview() {
    val state = ViewState(
        name = "",
        avatar = painterRes(DesignR.drawable.icon_boobs),
        isEdition = false
    )

    AppTheme {
        HateTopUnitEditScreen(
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