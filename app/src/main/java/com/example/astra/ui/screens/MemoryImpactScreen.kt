package com.example.astra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral
import com.example.astra.ui.theme.AstraNegativeRed

@Composable
fun MemoryImpactScreen(
    onBack: () -> Unit
) {
    var showWhyDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = AstraCoralPrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Dashboard", fontWeight = FontWeight.Bold, color = AstraCoralPrimary)
            }

            Surface(
                shape = CircleShape,
                color = AstraLightCoral
            ) {
                Text(
                    text = "+60% Win Rate Lift ✦",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = AstraDarkCoral,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Text(
            text = "Memory Impact",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Large Split-Screen Comparison (Generic AI vs ASTRA)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Left: Generic AI
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = CircleShape,
                            color = AstraNegativeRed.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Rounded.Cancel, contentDescription = null, tint = AstraNegativeRed, modifier = Modifier.size(14.dp))
                                Text("Generic AI", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = AstraNegativeRed)
                            }
                        }

                        Text(
                            text = "22% Win Rate",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Standard boilerplate responses without past deal memory.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("❌ Forgets objections", style = MaterialTheme.typography.labelSmall, color = AstraNegativeRed)
                            Text("❌ Generic emails", style = MaterialTheme.typography.labelSmall, color = AstraNegativeRed)
                            Text("❌ 0 Grounded evidence", style = MaterialTheme.typography.labelSmall, color = AstraNegativeRed)
                        }
                    }
                }
            }

            // Right: ASTRA
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = CircleShape,
                            color = AstraCoralPrimary
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                Text("ASTRA", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        Text(
                            text = "82% Win Rate",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AstraDarkCoral
                        )

                        Text(
                            text = "100% grounded in 17 specific deal memories.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AstraDarkCoral
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("✓ Retains CFO objections", style = MaterialTheme.typography.labelSmall, color = AstraDarkCoral)
                            Text("✓ Tailored follow-ups", style = MaterialTheme.typography.labelSmall, color = AstraDarkCoral)
                            Text("✓ Autonomous steering", style = MaterialTheme.typography.labelSmall, color = AstraDarkCoral)
                        }
                    }

                    Button(
                        onClick = { showWhyDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Why? Evidence 🧠", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Learning Curve Milestone Overview
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Memory Learning Curve", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("0 to 15+ experiences logged", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("90% Max Win Rate", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = AstraCoralPrimary)
            }
        }
    }

    if (showWhyDialog) {
        AlertDialog(
            onDismissRequest = { showWhyDialog = false },
            title = { Text("ASTRA Grounded Evidence ✦", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "ASTRA's 82% win rate is backed by 17 Hindsight episodic memory nodes from past enterprise deals. In 14 CFO negotiations, proposing a 90-day pilot resolved upfront cash-flow objections.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = { showWhyDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary)
                ) {
                    Text("Got it")
                }
            }
        )
    }
}
