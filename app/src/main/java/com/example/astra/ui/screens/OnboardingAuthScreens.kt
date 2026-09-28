package com.example.astra.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.CompareArrows
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral

@Composable
fun OnboardingScreen(
    onCompleteOnboarding: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top 3-step setup progress bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val stepTitles = listOf("1. CRM Link", "2. Memory Sync", "3. Active")
            stepTitles.forEachIndexed { index, title ->
                val stepNum = index + 1
                val isActive = stepNum <= currentStep
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(
                                if (isActive) AstraCoralPrimary else AstraLightCoral.copy(alpha = 0.4f)
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                        color = if (isActive) AstraDarkCoral else MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Center Content Graphic
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(AstraLightCoral.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val nodeA = Offset(center.x - 50f, center.y - 40f)
                val nodeB = Offset(center.x + 50f, center.y - 20f)
                val nodeC = Offset(center.x, center.y + 50f)

                drawLine(AstraCoralPrimary, nodeA, nodeB, strokeWidth = 3f)
                drawLine(AstraCoralPrimary, nodeB, nodeC, strokeWidth = 3f)
                drawLine(AstraCoralPrimary, nodeC, nodeA, strokeWidth = 3f)

                drawCircle(AstraCoralPrimary, 16f, nodeA)
                drawCircle(AstraDarkCoral, 18f, nodeB)
                drawCircle(AstraCoralPrimary, 14f, nodeC)
            }
            Icon(
                imageVector = if (currentStep == 1) Icons.Rounded.Psychology else if (currentStep == 2) Icons.AutoMirrored.Rounded.CompareArrows else Icons.Rounded.CheckCircle,
                contentDescription = null,
                tint = AstraDarkCoral,
                modifier = Modifier.size(48.dp)
            )
        }

        // Step Content Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = when (currentStep) {
                    1 -> "ASTRA remembers"
                    2 -> "See the difference"
                    else -> "Ready to close deals"
                },
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                text = when (currentStep) {
                    1 -> "Objections and stakeholder preferences automatically retained."
                    2 -> "Transform meeting logs into deal intelligence."
                    else -> "Your workspace is ready."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        // Action Button
        Button(
            onClick = {
                if (currentStep < 3) {
                    currentStep += 1
                } else {
                    onCompleteOnboarding()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AstraCoralPrimary,
                contentColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (currentStep < 3) "Continue →" else "Activate ASTRA 🧠",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit
) {
    var isSignUpTab by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("geethikadasari@gmail.com") }
    var password by remember { mutableStateOf("••••••••") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Memory Node Graphic Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(AstraLightCoral.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                drawLine(AstraCoralPrimary, Offset(w * 0.2f, h * 0.3f), Offset(w * 0.5f, h * 0.7f), strokeWidth = 3f)
                drawLine(AstraCoralPrimary, Offset(w * 0.5f, h * 0.7f), Offset(w * 0.8f, h * 0.4f), strokeWidth = 3f)
                drawCircle(AstraCoralPrimary, 12f, Offset(w * 0.2f, h * 0.3f))
                drawCircle(AstraDarkCoral, 16f, Offset(w * 0.5f, h * 0.7f))
                drawCircle(AstraCoralPrimary, 12f, Offset(w * 0.8f, h * 0.4f))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ASTRA",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AstraDarkCoral
                )
                Text(
                    text = "Sales Intelligence Platform",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Split Tab Control (Login vs Sign-up)
        TabRow(
            selectedTabIndex = if (isSignUpTab) 1 else 0,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = AstraCoralPrimary,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
        ) {
            Tab(
                selected = !isSignUpTab,
                onClick = { isSignUpTab = false },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("Log in", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            )
            Tab(
                selected = isSignUpTab,
                onClick = { isSignUpTab = true },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("Sign up", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            )
        }

        // Input Fields
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Work Email") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = null, tint = AstraCoralPrimary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null, tint = AstraCoralPrimary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Action Button
        Button(
            onClick = onAuthSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AstraCoralPrimary,
                contentColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isSignUpTab) "Create Account →" else "Log In →",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
