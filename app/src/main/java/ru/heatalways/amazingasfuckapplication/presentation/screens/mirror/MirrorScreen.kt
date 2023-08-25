@file:OptIn(ExperimentalPermissionsApi::class)

package ru.heatalways.amazingasfuckapplication.presentation.screens.mirror

import android.Manifest
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import ru.heatalways.amazingasfuckapplication.presentation.common.navigation.api.ScreenRoute
import ru.heatalways.amazingasfuckapplication.presentation.screens.mirror.MirrorContract.Intent
import ru.heatalways.amazingasfuckapplication.presentation.styles.AppTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object MirrorScreenRoute : ScreenRoute()

@Composable
fun MirrorScreen(viewModel: MirrorViewModel = koinViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    if (cameraPermissionState.status.isGranted) {
        val previewView: PreviewView = remember { PreviewView(context) }
        val preview = androidx.camera.core.Preview.Builder().build()

        preview.setSurfaceProvider(previewView.surfaceProvider)

        val cameraSelector: MutableState<CameraSelector> = remember {
            mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA)
        }

        LaunchedEffect(previewView) {
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

        MirrorPreview(
            previewFactory = { previewView },
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
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.menu_item_mirror),
                icon = painterResource(R.drawable.icon_mirror),
                containerColor = Color.Transparent,
                onGoBackClick = { onIntent(Intent.OnNavigationButtonClick) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { contentPadding ->
        AndroidView(
            factory = previewFactory,
            modifier = Modifier
                .fillMaxSize()
        )

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