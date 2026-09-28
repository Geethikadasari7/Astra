package com.example.astra.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral

@Composable
fun LandingScreen(
    onEnterDashboard: () -> Unit,
    onEnterDemo: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "OrbTransition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbPulse"
    )
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "OrbRotation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Pill Badge
        Surface(
            shape = CircleShape,
            color = AstraLightCoral.copy(alpha = 0.5f),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(AstraCoralPrimary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "✦ ASTRA 2.0 • INTEL ENGINE",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = AstraDarkCoral,
                    letterSpacing = 1.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Center Animated ASTRA Orb with Floating Nodes
        Box(
            modifier = Modifier
                .size(260.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val baseRadius = size.width * 0.28f * pulseScale

                // Outer ambient glow ring
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AstraCoralPrimary.copy(alpha = 0.35f),
                            AstraLightCoral.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = size.width * 0.45f
                    ),
                    center = center,
                    radius = size.width * 0.45f
                )

                // Outer rotating node orbit line
                drawCircle(
                    color = AstraCoralPrimary.copy(alpha = 0.25f),
                    center = center,
                    radius = baseRadius * 1.35f,
                    style = Stroke(width = 2f)
                )

                // Central Core Orb
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AstraCoralPrimary,
                            AstraDarkCoral
                        ),
                        center = center,
                        radius = baseRadius
                    ),
                    center = center,
                    radius = baseRadius
                )

                // Core inner highlight
                drawCircle(
                    color = Color.White.copy(alpha = 0.3f),
                    center = Offset(center.x - baseRadius * 0.3f, center.y - baseRadius * 0.3f),
                    radius = baseRadius * 0.25f
                )

                // 4 Floating Orbital Memory Nodes
                val nodeCount = 4
                for (i in 0 until nodeCount) {
                    val angleRad = Math.toRadians((rotationAngle + i * (360f / nodeCount)).toDouble())
                    val orbitRadius = baseRadius * 1.35f
                    val nodeX = center.x + (orbitRadius * Math.cos(angleRad)).toFloat()
                    val nodeY = center.y + (orbitRadius * Math.sin(angleRad)).toFloat()

                    // Connecting memory line to center
                    drawLine(
                        color = AstraCoralPrimary.copy(alpha = 0.4f),
                        start = center,
                        end = Offset(nodeX, nodeY),
                        strokeWidth = 2.dp.toPx()
                    )

                    // Node outer glow
                    drawCircle(
                        color = AstraLightCoral,
                        center = Offset(nodeX, nodeY),
                        radius = 12.dp.toPx()
                    )
                    // Node core
                    drawCircle(
                        color = AstraCoralPrimary,
                        center = Offset(nodeX, nodeY),
                        radius = 6.dp.toPx()
                    )
                }
            }
        }

        // Headline & Subtitle Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Meet ASTRA.",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Your sales memory, finally intelligent.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onEnterDashboard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AstraCoralPrimary,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Get started →",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            OutlinedButton(
                onClick = onEnterDemo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = AstraCoralPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Explore demo",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
