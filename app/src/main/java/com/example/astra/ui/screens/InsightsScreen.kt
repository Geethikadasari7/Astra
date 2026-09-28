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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astra.data.model.LearningPattern
import com.example.astra.data.model.Memory
import com.example.astra.ui.components.EvidenceBottomSheet
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral

data class EvidenceMemoryItem(
    val id: String,
    val category: String,
    val importance: Int,
    val stakeholder: String,
    val company: String,
    val quote: String,
    val timestamp: String
)

@Composable
fun InsightsScreen(
    patterns: List<LearningPattern>,
    initialTab: Int = 0,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) } // 0 = Learned, 1 = Evidence
    var showEvidenceSheet by remember { mutableStateOf(false) }
    var selectedPatternTitle by remember { mutableStateOf("") }

    val samplePatterns = patterns.ifEmpty {
        listOf(
            LearningPattern(
                id = "lp_pricing",
                pattern = "✦ Pricing Strategy: 90-Day Pilots close 34% faster with CFOs",
                frequency = 14,
                successRateImpact = 34.0f,
                recommendation = "Propose 90-day milestone pilots early when CFO budget objections arise.",
                category = "Pricing Strategy"
            ),
            LearningPattern(
                id = "lp_preference",
                pattern = "✦ Stakeholder Preference: Sub-50ms vector benchmark & SOC2 shortens CTO evaluation by 22 days",
                frequency = 9,
                successRateImpact = 22.0f,
                recommendation = "Highlight local vector speed & SOC2 certification during CTO review.",
                category = "Stakeholder Preference"
            ),
            LearningPattern(
                id = "lp_objection",
                pattern = "✦ Objection Pattern: Conceding Net-60 payment terms without multi-year extension reduces ARR",
                frequency = 6,
                successRateImpact = -15.0f,
                recommendation = "Require multi-year commitment whenever procurement requests Net-60 terms.",
                category = "Objection Pattern"
            )
        )
    }

    val sampleMemories = listOf(
        Memory("mem_01", "deal_acme_01", "CFO Sarah Jenkins objected to upfront annual billing without a risk-free 90-day pilot phase.", "OBJECTION", 0L, 5),
        Memory("mem_02", "deal_acme_01", "CTO Michael Chen confirmed SOC2 Type II certification is a non-negotiable blocker for production deployment.", "REQUIREMENT", 0L, 5),
        Memory("mem_04", "deal_acme_01", "Decision made to structure contract as a 90-day paid pilot with automatic conversion upon hitting KPI targets.", "DECISION", 0L, 5),
        Memory("mem_10", "deal_acme_01", "David Miller requested net-60 payment terms instead of net-30.", "OBJECTION", 0L, 3)
    )

    val evidenceItems = listOf(
        EvidenceMemoryItem(
            id = "ev_01",
            category = "OBJECTION",
            importance = 5,
            stakeholder = "Sarah Jenkins (CFO)",
            company = "Acme Financial",
            quote = "Requested 90-day milestone pilot prior to full sign-off.",
            timestamp = "2 days ago"
        ),
        EvidenceMemoryItem(
            id = "ev_02",
            category = "REQUIREMENT",
            importance = 5,
            stakeholder = "Michael Chen (CTO)",
            company = "Acme Financial",
            quote = "Verified sub-50ms local vector memory benchmark; confirmed SOC2 cert.",
            timestamp = "3 days ago"
        ),
        EvidenceMemoryItem(
            id = "ev_03",
            category = "NEGOTIATION",
            importance = 4,
            stakeholder = "David Miller (Procurement)",
            company = "Acme Financial",
            quote = "Pushed for Net-60 terms; counter-offered Net-30 with early settlement.",
            timestamp = "1 week ago"
        ),
        EvidenceMemoryItem(
            id = "ev_04",
            category = "COMPETITOR",
            importance = 4,
            stakeholder = "Marcus Vance (VP Sales)",
            company = "Nexus Technologies",
            quote = "Requested battlecard comparing ASTRA episodic memory retention.",
            timestamp = "1 week ago"
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
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
                            text = if (selectedTab == 0) "${samplePatterns.size} Discovered Patterns" else "${evidenceItems.size} Evidence Memories",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AstraDarkCoral,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Text(
                text = "Intelligence Insights",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Segmented Tab Bar: [ Learned ] [ Evidence ]
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = AstraCoralPrimary,
                        activeContentColor = Color.White,
                        inactiveContainerColor = MaterialTheme.colorScheme.surface,
                        inactiveContentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("Learned 💡", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }

                SegmentedButton(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = AstraCoralPrimary,
                        activeContentColor = Color.White,
                        inactiveContainerColor = MaterialTheme.colorScheme.surface,
                        inactiveContentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("Evidence 🧠", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }

            // Tab Content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (selectedTab == 0) {
                    // LEARNED TAB
                    items(samplePatterns) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = AstraLightCoral
                                    ) {
                                        Text(
                                            text = "${item.frequency} Deal Interactions",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = AstraDarkCoral,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }

                                    Surface(
                                        shape = CircleShape,
                                        color = AstraCoralPrimary
                                    ) {
                                        Text(
                                            text = "${if (item.successRateImpact > 0) "+" else ""}${item.successRateImpact.toInt()}% Impact",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = item.pattern,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Text(
                                    text = item.recommendation,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Button(
                                    onClick = {
                                        selectedPatternTitle = item.pattern
                                        showEvidenceSheet = true
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                        Text("View evidence (${item.frequency}) →", fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // EVIDENCE TAB
                    items(evidenceItems) { ev ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = AstraLightCoral
                                    ) {
                                        Text(
                                            text = ev.category,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = AstraDarkCoral,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }

                                    Text(
                                        text = "Importance ${ev.importance}/5",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Text(
                                    text = "\"${ev.quote}\"",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${ev.stakeholder} • ${ev.company}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = AstraCoralPrimary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = ev.timestamp,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showEvidenceSheet) {
            EvidenceBottomSheet(
                title = "✦ Evidence: $selectedPatternTitle",
                subtitle = "Grounded deal interaction memories supporting this learned pattern",
                explanation = "ASTRA analyzed historical deal logs across Acme Financial and previous enterprise accounts to validate this pattern.",
                supportingMemories = sampleMemories,
                onDismiss = { showEvidenceSheet = false }
            )
        }
    }
}
