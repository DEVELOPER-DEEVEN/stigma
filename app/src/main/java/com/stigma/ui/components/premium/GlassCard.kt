package com.stigma.ui.components.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stigma.ui.theme.*

/**
 * Premium glassmorphic card with blur effect and gradient border
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    borderWidth: Dp = 1.dp,
    glassOpacity: Float = 0.1f,
    enableGlow: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = modifier) {
        // Glow effect layer (optional)
        if (enableGlow) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(y = 4.dp)
                    .blur(24.dp)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                StigmaPrimary.copy(alpha = 0.3f),
                                StigmaSecondary.copy(alpha = 0.2f)
                            )
                        )
                    )
            )
        }
        
        // Glass card
        Card(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = borderWidth,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            StigmaGlassStroke,
                            StigmaGlassStroke.copy(alpha = 0.2f)
                        )
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                ),
            shape = RoundedCornerShape(cornerRadius),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = glassOpacity)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content
            )
        }
    }
}

/**
 * Elevated glass card with stronger blur and border
 */
@Composable
fun GlassCardElevated(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    GlassCard(
        modifier = modifier,
        cornerRadius = cornerRadius,
        borderWidth = 1.5.dp,
        glassOpacity = 0.15f,
        enableGlow = true,
        content = content
    )
}

/**
 * Glass card with holographic border gradient
 */
@Composable
fun HolographicGlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = modifier) {
        // Holographic glow
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 6.dp)
                .blur(32.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            StigmaNeonPurple.copy(alpha = 0.4f),
                            StigmaNeonBlue.copy(alpha = 0.3f),
                            StigmaNeonPink.copy(alpha = 0.2f)
                        )
                    )
                )
        )
        
        Card(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            StigmaNeonPurple,
                            StigmaNeonBlue,
                            StigmaNeonPink,
                            StigmaNeonPurple
                        )
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                ),
            shape = RoundedCornerShape(cornerRadius),
            colors = CardDefaults.cardColors(
                containerColor = StigmaSurfaceCard.copy(alpha = 0.9f)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content
            )
        }
    }
}
