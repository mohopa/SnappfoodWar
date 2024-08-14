package ir.mhp.kit.loading

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.mhp.presentation.R
import kotlin.random.Random

@Composable
fun LoadingView() {
    var height by remember {
        mutableStateOf(0)
    }
    var width by remember {
        mutableStateOf(0)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                width = it.size.width
                height = it.size.height
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Particles(height = height, width = width)
        LoadingImage(width = width)
    }
}

@Composable
fun LoadingImage(width: Int) {
    val rotateImageAnimatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = rotateImageAnimatable) {
        rotateImageAnimatable.animateTo(
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1500
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }
    val scaleImageAnimatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = scaleImageAnimatable) {
        scaleImageAnimatable.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1000
                    1.00f at 800 with LinearOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }
    val translateImageAnimatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = translateImageAnimatable) {
        translateImageAnimatable.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 4000
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }
    Image(
        painterResource(R.drawable.ic_millenniumfalcon),
        contentDescription = "spaceship loading",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .graphicsLayer {
                val scaleOffset = 0.5f
                val widthToTranslate = width - 150.dp.toPx()
                scaleX =
                    if (scaleImageAnimatable.value < scaleOffset) scaleX - (scaleImageAnimatable.value / 2) else scaleX - ((1f - scaleImageAnimatable.value) / 2)
                scaleY =
                    if (scaleImageAnimatable.value < scaleOffset) scaleY - (scaleImageAnimatable.value / 2) else scaleY - ((1f - scaleImageAnimatable.value) / 2)
                translationX =
                    if (translateImageAnimatable.value < 0.5f) widthToTranslate * translateImageAnimatable.value * 2 else widthToTranslate * (1f - translateImageAnimatable.value) * 2
                rotationY =
                    if (rotateImageAnimatable.value > 1f && rotateImageAnimatable.value <= 1.5f) (rotateImageAnimatable.value - 1f) * 720 else rotationY
            }
    )
}

@Composable
fun LoadingParticle(height: Int, position: Float) {
    with(LocalDensity.current) {
        val dpPos = position.toDp()
        val translationAnimatable = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = translationAnimatable) {
            translationAnimatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = Random.nextInt(500, 1000)
                        delayMillis = Random.nextInt(0, 1000)
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        Box(
            Modifier
                .fillMaxSize()
                .padding(dpPos, 0.dp, 0.dp, 0.dp)
        ) {
            if (translationAnimatable.value > 0)
                Divider(
                    thickness = Random.nextInt(20, 50).dp,
                    color = Color.White,
                    modifier = Modifier
                        .width(2.dp)
                        .graphicsLayer {
                            translationY = height * translationAnimatable.value
                        }
                        .alpha(
                            Random
                                .nextInt(50, 100)
                                .toFloat() / 100f
                        )
                )
        }
    }

}

@Composable
fun Particles(height: Int, width: Int) {
    for (i in 0..50) {
        LoadingParticle(height = height, position = (width.toFloat() / 50f) * i)
    }
}