package com.stigma.ui.components.premium

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stigma.ui.theme.*

/**
 * Pulsing dot indicator for real-time/live status
 */
@Composable
fun PulsingDot(
    modifier: Modifier = Modifier,
    size: Dp = 8.dp,
    color: Color = StigmaSuccess,
    pulseColor: Color = StigmaSuccessLight
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    
    Box(modifier = modifier.size(size * 2)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            // Outer pulsing circle
            drawCircle(
                color = pulseColor.copy(alpha = alpha),
                radius = (size.toPx() * scale) / 2
            )
            // Inner solid circle
            drawCircle(
                color = color,
                radius = size.toPx() / 2
            )
        }
    }
}

/**
 * Loading indicator with rotating gradient
 */
@Composable
fun NeonLoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Canvas(modifier = modifier.size(size)) {
        drawArc(
            brush = androidx.compose.ui.graphics.Brush.sweepGradient(
                colors = listOf(
                    StigmaPrimary,
                    StigmaSecondary,
                    StigmaTertiary,
                    Color.Transparent
                )
            ),
            startAngle = rotation,
            sweepAngle = 300f,
            useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4.dp.toPx())
        )
    }
}

/**
 * Progress bar with animated gradient
 */
@Composable
fun NeonProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 8.dp
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "progress"
    )
    
    Canvas(modifier = modifier.size(width = 200.dp, height = height)) {
        val width = size.width
        val h = size.height
        
        // Background track
        drawRoundRect(
            color = StigmaSurfaceElevated,
            size = size,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(h / 2)
        )
        
        // Progress fill with gradient
        if (animatedProgress > 0f) {
            drawRoundRect(
                brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                    colors = StigmaGradientPurpleBlue
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = width * animatedProgress.coerceIn(0f, 1f),
                    height = h
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(h / 2)
            )
        }
    }
}
