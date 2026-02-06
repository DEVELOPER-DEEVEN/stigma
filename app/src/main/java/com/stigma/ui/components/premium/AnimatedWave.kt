package com.stigma.ui.components.premium

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.stigma.ui.theme.*
import kotlin.math.PI
import kotlin.math.sin

/**
 * Animated wave pattern for premium visual effect
 */
@Composable
fun AnimatedWave(
    modifier: Modifier = Modifier,
    waveColor: List<androidx.compose.ui.graphics.Color> = StigmaGradientPurpleBlue,
    waveHeight: Float = 50f,
    waveSpeed: Float = 1f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (3000 / waveSpeed).toInt(),
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_offset"
    )
    
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(100.dp)) {
        val width = size.width
        val height = size.height
        val centerY = height / 2
        
        val path = Path().apply {
            moveTo(0f,centerY)
            
            for (x in 0..width.toInt()) {
                val y = centerY + sin((x / 100f + offset)) * waveHeight
                lineTo(x.toFloat(), y)
            }
            
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = waveColor.map { it.copy(alpha = 0.15f) }
            )
        )
    }
}

/**
 * Multiple layered waves for depth effect
 */
@Composable
fun LayeredWaves(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AnimatedWave(
            waveColor = listOf(StigmaPrimary.copy(alpha = 0.2f), StigmaSecondary.copy(alpha = 0.1f)),
            waveHeight = 40f,
            waveSpeed = 0.8f
        )
        AnimatedWave(
            waveColor = listOf(StigmaSecondary.copy(alpha = 0.15f), StigmaTertiary.copy(alpha = 0.08f)),
            waveHeight = 30f,
            waveSpeed = 1.2f
        )
        AnimatedWave(
            waveColor = listOf(StigmaTertiary.copy(alpha = 0.1f), StigmaNeonBlue.copy(alpha = 0.05f)),
            waveHeight = 20f,
            waveSpeed = 1.5f
        )
    }
}
