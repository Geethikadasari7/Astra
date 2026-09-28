package com.example.astra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.astra.data.model.MeetingBrief
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral
import com.example.astra.ui.theme.AstraNegativeRed
import com.example.astra.ui.viewmodel.BriefLoadingState

@Composable
fun AiMeetingBriefScreen(
    meetingBrief: MeetingBrief?,
    briefLoadingState: BriefLoadingState,
    loadingProgressText: String,
    onLoadBrief: () -> Unit,
    onResetBrief: () -> Unit,
    onShowWhy: (String) -> Unit,
    onBack: () -> Unit
) {
    var isMeetingStarted by remember { mutableStateOf(false) }

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
            TextButton(
                onClick = onBack,
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = AstraCoralPrimary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Dashboard", fontWeight = FontWeight.Bold, color = AstraCoralPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                if (briefLoadingState == BriefLoadingState.LOADING) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = AstraCoralPrimary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(loadingProgressText.take(15) + "...", style = MaterialTheme.typography.labelSmall, color = AstraCoralPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                } else {
                    IconButton(onClick = { onResetBrief(); onLoadBrief() }) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "Refresh", tint = AstraCoralPrimary, modifier = Modifier.size(20.dp))
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = AstraLightCoral
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Geethika Dasari",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AstraDarkCoral,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Greeting Banner Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AstraCoralPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "GD",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Column {
                    Text(
                        text = "Geethika Dasari",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = AstraDarkCoral,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "ASTRA memory brief for upcoming Acme Financial meeting.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Text(
            text = meetingBrief?.title ?: "Acme Negotiation Prep",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // 4 Visual Blocks Grid (Remember, Focus, Avoid, Recommend)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Block 1: 🧠 Remember
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Rounded.Psychology, contentDescription = null, tint = AstraCoralPrimary, modifier = Modifier.size(20.dp))
                            Text("Remember", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Text(
                            text = "Sarah (CFO) rejected upfront annual commitment.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Block 2: 🎯 Focus
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Rounded.TrackChanges, contentDescription = null, tint = AstraDarkCoral, modifier = Modifier.size(20.dp))
                            Text("Focus", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Text(
                            text = "Align on 90-day milestone pilot and Michael's CTO sign-off.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AstraDarkCoral
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Block 3: ⚠️ Avoid
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Rounded.Warning, contentDescription = null, tint = AstraNegativeRed, modifier = Modifier.size(20.dp))
                            Text("Avoid", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Text(
                            text = "Avoid flat discounts without milestone commitments.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Block 4: ✦ Recommend
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Rounded.AutoAwesome, contentDescription = null, tint = AstraDarkCoral, modifier = Modifier.size(20.dp))
                                Text("Recommend", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            TextButton(onClick = { onShowWhy("rec_01") }, contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Text("Why?", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                        Text(
                            text = "Propose 90-day milestone paid pilot.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AstraDarkCoral
                        )
                    }
                }
            }
        }

        // Prominent Primary Action Button: "Start meeting →"
        Button(
            onClick = { isMeetingStarted = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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
                Icon(
                    imageVector = if (isMeetingStarted) Icons.Rounded.Mic else Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isMeetingStarted) "Meeting Live (Recording) 🔴" else "Start meeting →",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
