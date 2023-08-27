@file:OptIn(ExperimentalPermissionsApi::class)

package ru.heatalways.amazingasfuckapplication.presentation.screens.mirror

import android.Manifest
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel
import ru.heatalways.amazingasfuckapplication.R
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.AppBar
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.Heart
import ru.heatalways.amazingasfuckapplication.presentation.common.composables.radialBackgroundLighting
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.roundToInt

object MirrorScreenRoute : ScreenRoute()

private const val HEARTS_COUNT = 20
private const val HEART_MIN_SIZE_DP = 30
private const val HEART_MAX_SIZE_DP = 100
private const val HEART_MIN_ROTATION = -45
private const val HEART_MAX_ROTATION = 45
private const val HEART_ANIMATION_DURATION = 5000

@Composable
fun MirrorScreen(viewModel: MirrorViewModel = koinViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    val preview = androidx.camera.core.Preview.Builder().build()

    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA)
    }

    LaunchedEffect(Unit) {
        val cameraProvider = suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).also { future ->
                future.addListener(
                    { continuation.resume(future.get()) },
                    ContextCompat.getMainExecutor(context)
                )
            }
        }

        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector.value,
            preview,
        )
    }

    if (cameraPermissionState.status.isGranted) {
        MirrorPreview(
            previewFactory = {
                val previewView = PreviewView(context)
                preview.setSurfaceProvider(previewView.surfaceProvider)
                previewView
            },
            onIntent = viewModel::intent,
        )
    } else {
        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}

@Composable
private fun MirrorPreview(
    previewFactory: (Context) -> PreviewView,
    onIntent: (Intent) -> Unit
) {
    var screenWidth by remember { mutableIntStateOf(0) }
    var screenHeight by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.menu_item_mirror),
                icon = painterResource(R.drawable.icon_mirror),
                containerColor = Color.Transparent,
                onGoBackClick = { onIntent(Intent.OnNavigationButtonClick) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .onGloballyPositioned {
                screenWidth = it.size.width
                screenHeight = it.size.height
            }
    ) { contentPadding ->
        AndroidView(
            factory = previewFactory,
            modifier = Modifier
                .fillMaxSize()
        )

        repeat(HEARTS_COUNT) { index ->
            if (screenHeight == 0 || screenWidth == 0) {
                return@repeat
            }

            val rotation = (HEART_MIN_ROTATION..HEART_MAX_ROTATION).random().toFloat()
            val heartSize = (HEART_MIN_SIZE_DP..HEART_MAX_SIZE_DP).random().dp
            val heartSizePx = with(LocalDensity.current) { heartSize.toPx() }

            val catLoaderTransition = rememberInfiniteTransition(label = "HeartTransition-$index")

            val x = (0..screenWidth - heartSizePx.roundToInt()).random().toFloat()

            val y by catLoaderTransition.animateFloat(
                initialValue = screenHeight + heartSizePx,
                targetValue = -heartSizePx,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = HEART_ANIMATION_DURATION,
                        delayMillis = (0..HEART_ANIMATION_DURATION).random(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                label = "translation-$index"
            )

            Heart(
                strokeColor = AppTheme.colors.primary,
                modifier = Modifier
                    .size(width = heartSize, height = heartSize)
                    .graphicsLayer {
                        translationX = x
                        translationY = y
                        rotationZ = rotation
                    }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(contentPadding.calculateTopPadding() * 1.5f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            AppTheme.colors.background,
                            Color.Transparent,
                        ),
                    )
                )
        )
    }
}

@Composable
@Preview
private fun MirrorScreenPreview() {
    AppTheme {
        MirrorScreen()
    }
}