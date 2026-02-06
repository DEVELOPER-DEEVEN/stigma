package com.stigma.ui.components.premium

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.stigma.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Particle data class
 */
private data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var size: Float,
    var alpha: Float,
    val color: Color
)

/**
 * Animated particle background for premium visual effect
 */
@Composable
fun ParticleBackground(
    modifier: Modifier = Modifier,
    particleCount: Int = 50,
    animate: Boolean = true
) {
    var particles by remember {
        mutableStateOf(
            List(particleCount) {
                createRandomParticle()
            }
        )
    }
    
    // Animation
    if (animate) {
        LaunchedEffect(Unit) {
            while (true) {
                withFrameMillis { frameTimeMillis ->
                    particles = particles.map { particle ->
                        updateParticle(particle)
                    }
                }
            }
        }
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            drawCircle(
                color = particle.color.copy(alpha = particle.alpha),
                radius = particle.size,
                center = Offset(particle.x, particle.y)
            )
        }
    }
}

private fun createRandomParticle(): Particle {
    val colors = listOf(
        StigmaParticlePurple,
        StigmaParticleBlue,
        StigmaParticleCyan,
        StigmaParticlePink
    )
    
    return Particle(
        x = Random.nextFloat() * 2000f,
        y = Random.nextFloat() * 2000f,
        vx = (Random.nextFloat() - 0.5f) * 0.5f,
        vy = (Random.nextFloat() - 0.5f) * 0.5f,
        size = Random.nextFloat() * 3f + 1f,
        alpha = Random.nextFloat() * 0.3f + 0.1f,
        color = colors.random()
    )
}

private fun updateParticle(particle: Particle): Particle {
    var newX = particle.x + particle.vx
    var newY = particle.y + particle.vy
    var newVx = particle.vx
    var newVy = particle.vy
    
    // Bounce off edges (assuming screen size)
    if (newX < 0 || newX > 2000f) {
        newVx = -newVx
        newX = particle.x
    }
    if (newY < 0 || newY > 2000f) {
        newVy = -newVy
        newY = particle.y
    }
    
    return particle.copy(
        x = newX,
        y = newY,
        vx = newVx,
        vy = newVy
    )
}

/**
 * Floating orbs background effect
 */
@Composable
fun FloatingOrbsBackground(
    modifier: Modifier = Modifier,
    orbCount: Int = 5
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orbs")
    
    val orbs = remember {
        List(orbCount) { index ->
            Triple(
                Random.nextFloat() * 1000f, // x
                Random.nextFloat() * 1000f, // y
                Random.nextFloat() * 100f + 150f // size
            )
        }
    }
    
    val animatedOffsets = orbs.mapIndexed { index, (x, y, size) ->
        val offsetX by infiniteTransition.animateFloat(
            initialValue = x,
            targetValue = x + (Random.nextFloat() * 200f - 100f),
            animationSpec = infiniteRepeatable(
                animation = tween((3000 + index * 500), easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "orbX$index"
        )
        
        val offsetY by infiniteTransition.animateFloat(
            initialValue = y,
            targetValue = y + (Random.nextFloat() * 200f - 100f),
            animationSpec = infiniteRepeatable(
                animation = tween((3500 + index * 500), easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "orbY$index"
        )
        
        Triple(offsetX, offsetY, size)
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        animatedOffsets.forEachIndexed { index, (x, y, size) ->
            val colors = listOf(
                StigmaPrimary,
                StigmaSecondary,
                StigmaTertiary,
                StigmaNeonPurple,
                StigmaNeonBlue
            )
            
            drawCircle(
                color = colors[index % colors.size].copy(alpha = 0.06f),
                radius = size,
                center = Offset(x, y)
            )
        }
    }
}
