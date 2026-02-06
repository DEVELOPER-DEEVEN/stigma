package com.stigma.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = StigmaPrimary,
    secondary = StigmaSecondary,
    tertiary = StigmaTertiary,
    surface = StigmaSurface,
    background = StigmaSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onSurface = StigmaTextPrimary,
    onBackground = StigmaTextPrimary
)

@Composable
fun StigmaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
