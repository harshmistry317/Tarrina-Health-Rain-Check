package com.hkm.tarrina_health_rain_check.composecommons

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun FloatingElements() {
    val animationDuration = 6000
    val infiniteTransition = rememberInfiniteTransition(label = "floating")

    val offsets = listOf(
        Pair(0.1f, 0.15f),
        Pair(0.8f, 0.25f),
        Pair(0.2f, 0.4f),
        Pair(0.9f, 0.6f),
        Pair(0.15f, 0.75f)
    )

    offsets.forEachIndexed { index, (x, y) ->
        val animatedY by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = -20f,
            animationSpec = infiniteRepeatable(
                animation = tween(animationDuration, easing = EaseInOutSine),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(index * 1000)
            ),
            label = "float$index"
        )

        BoxWithConstraints {
            val size = when (index % 4) {
                0 -> 48.dp
                1 -> 32.dp
                2 -> 24.dp
                else -> 40.dp
            }

            Box(
                modifier = Modifier
                    .offset(
                        x = (maxWidth * x),
                        y = (maxHeight * y) + animatedY.dp
                    )
                    .size(size)
                    .background(
                        Color.White.copy(alpha = 0.1f + (index * 0.02f)),
                        CircleShape
                    )
            )
        }
    }
}