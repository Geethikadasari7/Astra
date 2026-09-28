package com.example.astra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.astra.data.model.Deal
import com.example.astra.data.model.DealStatus
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral

@Composable
fun DealsPageScreen(
    deal: Deal?,
    initialTab: Int = 0,
    onSelectDeal: () -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) } // 0 = Deals, 1 = Accounts

    val dealsList = listOf(
        Deal(
            id = "deal_acme_01",
            title = "Enterprise AI Sales Agent Rollout",
            company = "Acme Financial",
            stage = "Negotiation",
            value = 450000.0,
            closeDate = "2025-09-30",
            healthScore = 88,
            status = DealStatus.IN_PROGRESS,
            summary = "Evaluating $450K AI deployment for 120 reps."
        ),
        Deal(
            id = "deal_nexus_02",
            title = "Autonomous Follow-up Engine",
            company = "Nexus Technologies",
            stage = "Proposal",
            value = 320000.0,
            closeDate = "2025-10-15",
            healthScore = 92,
            status = DealStatus.IN_PROGRESS,
            summary = "Expanding vector memory integration."
        ),
        Deal(
            id = "deal_global_03",
            title = "Supply Chain Intelligence Assistant",
            company = "Global Logistics Corp",
            stage = "Discovery",
            value = 180000.0,
            closeDate = "2025-11-01",
            healthScore = 78,
            status = DealStatus.IN_PROGRESS,
            summary = "Evaluating memory retention for logistics."
        ),
        Deal(
            id = "deal_apex_04",
            title = "HIPAA Compliant Patient Memory Agent",
            company = "Apex Healthcare",
            stage = "Security Review",
            value = 290000.0,
            closeDate = "2025-10-31",
            healthScore = 85,
            status = DealStatus.IN_PROGRESS,
            summary = "SOC2 Type II and HIPAA privacy review."
        )
    )

    val accountsList = listOf(
        AccountItem("Acme Financial", "Banking & FinTech", 1, "$450,000", 17),
        AccountItem("Nexus Technologies", "Enterprise SaaS", 2, "$320,000", 12),
        AccountItem("Global Logistics Corp", "Supply Chain", 1, "$180,000", 8),
        AccountItem("Apex Healthcare", "Medical Devices", 1, "$290,000", 9)
    )

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
                        text = if (selectedTab == 0) "${dealsList.size} Active Deals" else "${accountsList.size} Key Accounts",
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
            text = "Pipeline & Accounts",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Top Segmented Tab Bar: [ Deals ] [ Accounts ]
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
                    Text("Deals 💼", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
                    Text("Accounts 🏢", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }

        // Tab Content List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (selectedTab == 0) {
                // DEALS TAB
                items(dealsList) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectDeal() },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(AstraLightCoral),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = item.company.take(2).uppercase(),
                                            fontWeight = FontWeight.Bold,
                                            color = AstraDarkCoral
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = item.company,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = item.title,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Surface(
                                    shape = CircleShape,
                                    color = AstraCoralPrimary
                                ) {
                                    Text(
                                        text = "${item.healthScore}% Health",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = AstraLightCoral.copy(alpha = 0.6f)
                                    ) {
                                        Text(
                                            text = "Stage: ${item.stage}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = AstraDarkCoral,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "$${String.format("%,.0f", item.value)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = AstraDarkCoral,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            } else {
                // ACCOUNTS TAB
                items(accountsList) { acc ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectDeal() },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(AstraLightCoral),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(acc.name.take(2).uppercase(), fontWeight = FontWeight.Bold, color = AstraDarkCoral)
                                }

                                Column {
                                    Text(acc.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text("${acc.industry} • ${acc.totalValue}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color = AstraLightCoral
                            ) {
                                Text(
                                    text = "${acc.memoryNodes} Memory Nodes",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AstraDarkCoral,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
