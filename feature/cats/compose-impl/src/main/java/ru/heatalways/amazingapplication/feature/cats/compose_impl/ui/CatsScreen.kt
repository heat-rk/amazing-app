package ru.heatalways.amazingapplication.feature.cats.compose_impl.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Size
import ru.heatalways.amazingapplication.core.composables.pager.PagerScreen
import ru.heatalways.amazingapplication.core.design.composables.radialBackgroundLighting
import ru.heatalways.amazingapplication.core.design.styles.AppTheme
import ru.heatalways.amazingapplication.core.design.styles.Sizes
import ru.heatalways.amazingapplication.feature.cats.compose_impl.di.CatsComponent
import ru.heatalways.amazingapplication.core.design.R as DesignR

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = viewModel(factory = CatsComponent.catsViewModelFactory),
    title: String,
) {
    PagerScreen(
        viewModel = viewModel,
        title = title,
        icon = painterResource(DesignR.drawable.icon_cat),
        content = {
            CatsScreenContent(url = it)
        },
        contentShimmer = { /* empty shimmer */ }
    )
}

@Composable
private fun CatsScreenContent(url: String) {
    val context = LocalContext.current
    val imageLoader = context.imageLoader
    var isLoaded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(url)
                    .apply { size(Size.ORIGINAL) }
                    .build(),
                imageLoader = imageLoader,
                onState = { state ->
                    isLoaded = state is AsyncImagePainter.State.Success
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )

        if (!isLoaded) {
            CatsScreenContentShimmer(
                modifier = Modifier
                    .height(Sizes.CatLoaderHeight)
                    .align(
                        BiasAlignment(
                            horizontalBias = 0f,
                            verticalBias = -0.5f,
                        )
                    )
            )
        }
    }
}

@Composable
private fun CatsScreenContentShimmer(
    modifier: Modifier = Modifier
) {
    val catLoaderTransition = rememberInfiniteTransition(label = "CatLoaderTransition")

    val rotation by catLoaderTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Restart),
        label = "rotation"
    )

    Image(
        painter = painterResource(DesignR.drawable.icon_cat),
        colorFilter = ColorFilter.tint(AppTheme.colors.primary),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = modifier
            .graphicsLayer {
                transformOrigin = TransformOrigin.Center
                rotationZ = rotation
            }
            .radialBackgroundLighting(AppTheme.colors.primary)
    )
}