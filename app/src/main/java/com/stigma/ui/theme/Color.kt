package com.stigma.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Brand Colors
val StigmaPrimary = Color(0xFF8B5CF6)  // Vibrant Purple
val StigmaSecondary = Color(0xFF3B82F6)  // Electric Blue
val StigmaTertiary = Color(0xFF06B6D4)  // Cyan

// Neon Accent Colors
val StigmaNeonPurple = Color(0xFFA855F7)
val StigmaNeonBlue = Color(0xFF60A5FA)
val StigmaNeonPink = Color(0xFFEC4899)
val StigmaNeonCyan = Color(0xFF22D3EE)

// Surface Colors
val StigmaSurface = Color(0xFF0F0F1E)
val StigmaSurfaceCard = Color(0xFF1A1A2E)
val StigmaSurfaceElevated = Color(0xFF16213E)

// Text Colors
val StigmaTextPrimary = Color(0xFFFFFFFF)
val StigmaTextSecondary = Color(0xFFB0B0C0)

// Status Colors
val StigmaSuccess = Color(0xFF10B981)
val StigmaSuccessLight = Color(0xFF34D399)
val StigmaWarning = Color(0xFFF59E0B)
val StigmaError = Color(0xFFEF4444)

// Glass/Glow Effects
val StigmaGlassStroke = Color(0x40FFFFFF)

// Particle Colors
val StigmaParticlePurple = Color(0x60A855F7)
val StigmaParticleBlue = Color(0x6060A5FA)
val StigmaParticleCyan = Color(0x6022D3EE)
val StigmaParticlePink = Color(0x60EC4899)

// Shimmer Colors
val StigmaShimmerBase = Color(0x20FFFFFF)
val StigmaShimmerHighlight = Color(0x40FFFFFF)

// Gradient Brushes
val StigmaGradientPurpleBlue = Brush.linearGradient(
    colors = listOf(StigmaPrimary, StigmaSecondary)
)

val StigmaGradientTriple = Brush.linearGradient(
    colors = listOf(StigmaPrimary, StigmaSecondary, StigmaTertiary)
)

val StigmaGradientNeon = Brush.linearGradient(
    colors = listOf(StigmaNeonPurple, StigmaNeonBlue, StigmaNeonPink)
)
