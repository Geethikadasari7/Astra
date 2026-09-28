package com.example.astra.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astra.data.model.Deal
import com.example.astra.data.model.Memory
import com.example.astra.data.model.Stakeholder
import com.example.astra.data.model.TimelineEvent
import com.example.astra.ui.components.EvidenceBottomSheet
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral
import com.example.astra.ui.theme.AstraNegativeRed

@Composable
fun AcmeDealDetailScreen(
    deal: Deal?,
    stakeholders: List<Stakeholder>,
    memories: List<Memory>,
    timelineEvents: List<TimelineEvent>,
    onShowWhy: (String) -> Unit,
    onNavigateToTimeMachine: () -> Unit = {},
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showEvidenceSheet by remember { mutableStateOf(false) }
    val tabs = listOf("Overview", "Timeline", "People", "Memory")

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Back Button & Stage Badge
            item {
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
                            Text("Deals", fontWeight = FontWeight.Bold, color = AstraCoralPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
                                text = "Negotiation Stage",
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

            // Deal Header
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = deal?.company ?: "Acme Financial",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = deal?.title ?: "Enterprise AI Sales Agent Rollout",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 4 Compact Visual Metric Blocks
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Metric 1: Health Score
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text("HEALTH", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("${deal?.healthScore ?: 88}%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = AstraCoralPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }

                    // Metric 2: Value
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text("VALUE", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("$450,000", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }

                    // Metric 3: Days in Stage
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text("IN STAGE", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("14d", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }

                    // Metric 4: Memory Velocity
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text("MEMORY", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("${memories.size} nodes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }

            // Tab Row (Overview, Timeline, People, Memory)
            item {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 0.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = AstraCoralPrimary,
                    indicator = { tabPositions ->
                        if (selectedTab < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = AstraCoralPrimary
                            )
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == index) AstraCoralPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }
            }

            // Tab Content Views
            when (selectedTab) {
                0 -> {
                    // Deal Time Machine Entry Banner
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, AstraCoralPrimary.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.History,
                                        contentDescription = null,
                                        tint = AstraCoralPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Column {
                                        Text("Deal Time Machine", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text("Historical memory evolution", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }

                                Button(
                                    onClick = onNavigateToTimeMachine,
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                        Text("Explore →", fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                        }
                    }

                    // Overview Strategy Card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Deal Strategy Overview",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Evaluating $450K AI rollout. Key decision makers: Sarah (CFO) & Michael (CTO).",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Key Strategic Action
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "✦ Key Strategic Action",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = AstraDarkCoral
                                    )
                                    TextButton(onClick = { onShowWhy("rec_01") }, contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                            Text("Why?", fontWeight = FontWeight.Bold, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        }
                                    }
                                }
                                Text(
                                    text = "Propose 90-day pilot to resolve CFO budget freeze.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AstraDarkCoral
                                )
                            }
                        }
                    }

                    // USP #1: OUTCOME INTELLIGENCE SECTION
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Psychology,
                                            contentDescription = null,
                                            tint = AstraCoralPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "Outcome Intelligence",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Surface(
                                        shape = CircleShape,
                                        color = AstraLightCoral
                                    ) {
                                        Text(
                                            text = "ACTION ➔ OUTCOME",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = AstraDarkCoral,
                                            fontSize = 8.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                // Visual Flow Item 1: Discount Strategy
                                OutcomeFlowCard(
                                    action = "Discount 10%",
                                    response = "❌ Rejected by CFO",
                                    outcome = "Deal Stalled",
                                    learning = "Avoid unanchored price cuts.",
                                    isSuccess = false
                                )

                                // Visual Flow Item 2: ROI & Pilot Strategy
                                OutcomeFlowCard(
                                    action = "ROI & 90-Day Pilot",
                                    response = "✓ Approved by CFO",
                                    outcome = "Deal Progressed",
                                    learning = "Lead with ROI & risk-free pilot.",
                                    isSuccess = true
                                )

                                // Visual Flow Item 3: Tech Validation Strategy
                                OutcomeFlowCard(
                                    action = "Sub-50ms Latency Demo",
                                    response = "✓ Approved by CTO",
                                    outcome = "Tech Sign-off",
                                    learning = "Provide local vector memory benchmark.",
                                    isSuccess = true
                                )

                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                                // "✦ ASTRA LEARNED" Highlight Box
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = AstraLightCoral.copy(alpha = 0.45f),
                                    border = BorderStroke(1.dp, AstraCoralPrimary.copy(alpha = 0.3f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "✦ ASTRA LEARNED",
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = AstraDarkCoral
                                            )

                                            Surface(
                                                shape = CircleShape,
                                                color = AstraCoralPrimary
                                            ) {
                                                Text(
                                                    text = "3 interactions",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                                )
                                            }
                                        }

                                        Text(
                                            text = "CFO Sarah Jenkins converts on 90-day milestone pilots. Flat price discounting fails to overcome budget freezes.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AstraDarkCoral,
                                            fontWeight = FontWeight.Medium
                                        )

                                        Button(
                                            onClick = { showEvidenceSheet = true },
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                            modifier = Modifier.align(Alignment.End)
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                                Text("View evidence →", fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Timeline
                    items(timelineEvents) { event ->
                        val isProgressed = event.outcome.contains("progress", ignoreCase = true) || event.stage.contains("progress", ignoreCase = true)
                        val isFailed = !event.success || event.outcome.contains("fail", ignoreCase = true) || event.stage.contains("fail", ignoreCase = true)

                        val (outcomeBg, outcomeFg, outcomeText) = when {
                            isProgressed -> Triple(Color(0xFFE3F2FD), Color(0xFF1565C0), "→ Progressed")
                            isFailed -> Triple(AstraNegativeRed.copy(alpha = 0.15f), AstraNegativeRed, "× Failed")
                            else -> Triple(AstraLightCoral, AstraDarkCoral, "✓ Successful")
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = event.stage,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Surface(
                                        shape = CircleShape,
                                        color = outcomeBg
                                    ) {
                                        Text(
                                            text = outcomeText,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = outcomeFg,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = event.stakeholder,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = AstraCoralPrimary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = event.timestamp,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Text(
                                    text = event.outcome,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                2 -> {
                    // People (Stakeholders)
                    items(stakeholders) { person ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(AstraLightCoral),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = person.name.take(1),
                                            fontWeight = FontWeight.Bold,
                                            color = AstraDarkCoral
                                        )
                                    }
                                    Column {
                                        Text(text = person.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text(text = person.role, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }

                                Surface(
                                    shape = CircleShape,
                                    color = AstraLightCoral
                                ) {
                                    Text(
                                        text = person.sentiment,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = AstraDarkCoral,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                3 -> {
                    // Memory
                    items(memories) { memory ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = memory.category,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = AstraCoralPrimary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Importance ${memory.importance}/5",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Text(
                                    text = memory.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        // Evidence Drawer / Bottom Sheet
        if (showEvidenceSheet) {
            EvidenceBottomSheet(
                title = "✦ Why ASTRA thinks this",
                subtitle = "Grounded deal memories behind Outcome Intelligence",
                explanation = "ASTRA evaluated 3 recent interaction cycles for Acme Financial. Proposing a 90-day pilot resolved CFO cash-flow objections in 100% of cases.",
                supportingMemories = memories.take(3),
                onDismiss = { showEvidenceSheet = false }
            )
        }
    }
}

@Composable
private fun OutcomeFlowCard(
    action: String,
    response: String,
    outcome: String,
    learning: String,
    isSuccess: Boolean
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSuccess) AstraLightCoral.copy(alpha = 0.3f) else AstraNegativeRed.copy(alpha = 0.08f),
        border = BorderStroke(1.dp, if (isSuccess) AstraCoralPrimary.copy(alpha = 0.2f) else AstraNegativeRed.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("ACTION:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(action, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                Surface(
                    shape = CircleShape,
                    color = if (isSuccess) AstraLightCoral else AstraNegativeRed.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = response,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isSuccess) AstraDarkCoral else AstraNegativeRed,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("OUTCOME:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(outcome, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = if (isSuccess) AstraDarkCoral else AstraNegativeRed, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("LEARNING:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, fontSize = 9.sp, color = AstraCoralPrimary)
                Text(learning, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
