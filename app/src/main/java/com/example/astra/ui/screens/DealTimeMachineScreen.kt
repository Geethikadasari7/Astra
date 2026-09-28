package com.example.astra.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
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
import androidx.compose.ui.unit.sp
import com.example.astra.data.model.Memory
import com.example.astra.ui.components.EvidenceBottomSheet
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral
import com.example.astra.ui.theme.AstraNegativeRed

data class TimeMachineStep(
    val dayNumber: Int,
    val stageTitle: String,
    val subtitle: String,
    val astraKnew: String,
    val whatChanged: String,
    val activeNodes: List<String>,
    val evidenceMemories: List<Memory>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealTimeMachineScreen(
    memories: List<Memory>,
    onBack: () -> Unit
) {
    var selectedStepIndex by remember { mutableIntStateOf(3) } // Default to Day 23
    var showEvidenceSheet by remember { mutableStateOf(false) }
    var selectedDealName by remember { mutableStateOf("Acme Financial") }
    var showDealMenu by remember { mutableStateOf(false) }

    val steps = listOf(
        TimeMachineStep(
            dayNumber = 1,
            stageTitle = "Day 1 — Discovery",
            subtitle = "Initial deal setup",
            astraKnew = "Acme Financial evaluating AI sales agent rollout for 1,200 reps. Primary stack is Salesforce CRM.",
            whatChanged = "Initial opportunity created and baseline requirements logged.",
            activeNodes = listOf("Integration"),
            evidenceMemories = listOf(
                memories.find { it.id == "mem_01" } ?: Memory("mem_01", "deal_acme_01", "CFO Sarah Jenkins objected to upfront annual billing without a risk-free 90-day pilot phase.", "OBJECTION", 0L, 5),
                memories.find { it.id == "mem_09" } ?: Memory("mem_09", "deal_acme_01", "Marcus Vance highlighted that sales reps spend 4 hours daily updating CRM manually.", "REQUIREMENT", 0L, 4)
            )
        ),
        TimeMachineStep(
            dayNumber = 7,
            stageTitle = "Day 7 — Technical Validation",
            subtitle = "Architecture & InfoSec deep dive",
            astraKnew = "CTO Michael Chen requires SOC2 Type II compliance and sub-50ms local vector memory benchmark.",
            whatChanged = "Technical deep-dive conducted. CTO praised Kotlin Coroutines architecture.",
            activeNodes = listOf("Integration", "Pricing"),
            evidenceMemories = listOf(
                memories.find { it.id == "mem_02" } ?: Memory("mem_02", "deal_acme_01", "CTO Michael Chen confirmed SOC2 Type II certification is a non-negotiable blocker for production deployment.", "REQUIREMENT", 0L, 5),
                memories.find { it.id == "mem_08" } ?: Memory("mem_08", "deal_acme_01", "Michael Chen praised the robust Kotlin Coroutines concurrency model and clean architecture.", "REQUIREMENT", 0L, 3),
                memories.find { it.id == "mem_13" } ?: Memory("mem_13", "deal_acme_01", "Technical deep-dive with Michael Chen successfully resolved API rate-limiting concerns.", "DECISION", 0L, 4)
            )
        ),
        TimeMachineStep(
            dayNumber = 15,
            stageTitle = "Day 15 — CFO Meeting",
            subtitle = "Commercial negotiation",
            astraKnew = "CFO Sarah Jenkins rejected $450K upfront commitment. Generic 10% discount attempt failed.",
            whatChanged = "ASTRA recommended pivoting from price discount to a risk-free 90-day paid pilot.",
            activeNodes = listOf("Integration", "Pricing", "ROI"),
            evidenceMemories = listOf(
                memories.find { it.id == "mem_01" } ?: Memory("mem_01", "deal_acme_01", "CFO Sarah Jenkins objected to upfront annual billing without a risk-free 90-day pilot phase.", "OBJECTION", 0L, 5),
                memories.find { it.id == "mem_04" } ?: Memory("mem_04", "deal_acme_01", "Decision made to structure contract as a 90-day paid pilot with automatic conversion upon hitting KPI targets.", "DECISION", 0L, 5),
                memories.find { it.id == "mem_05" } ?: Memory("mem_05", "deal_acme_01", "Past deal failure with TechGlobal resulted from inadequate security review documentation during procurement.", "EXPERIENCE", 0L, 4)
            )
        ),
        TimeMachineStep(
            dayNumber = 23,
            stageTitle = "Day 23 — Final Contract",
            subtitle = "Milestone pilot agreement",
            astraKnew = "90-day pilot accepted by CFO. InfoSec audit approved. Procurement agreed to Net-30 terms.",
            whatChanged = "Pilot agreement finalized. Full contract delivered for signature. Deal health score at 88/100.",
            activeNodes = listOf("Integration", "Pricing", "ROI", "Strategy"),
            evidenceMemories = listOf(
                memories.find { it.id == "mem_04" } ?: Memory("mem_04", "deal_acme_01", "Decision made to structure contract as a 90-day paid pilot with automatic conversion upon hitting KPI targets.", "DECISION", 0L, 5),
                memories.find { it.id == "mem_15" } ?: Memory("mem_15", "deal_acme_01", "Security questionnaire completed and approved by Acme's InfoSec team.", "DECISION", 0L, 5),
                memories.find { it.id == "mem_16" } ?: Memory("mem_16", "deal_acme_01", "Verbal agreement obtained from CTO Michael Chen for Q3 budget allocation.", "DECISION", 0L, 5),
                memories.find { it.id == "mem_17" } ?: Memory("mem_17", "deal_acme_01", "Final contract review scheduled with legal team for next Tuesday.", "DECISION", 0L, 4)
            )
        )
    )

    val currentStep = steps[selectedStepIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Back Button & Top Action
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
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = AstraCoralPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Memory", fontWeight = FontWeight.Bold, color = AstraCoralPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            // Deal Selector Dropdown
            Box {
                Surface(
                    onClick = { showDealMenu = true },
                    shape = RoundedCornerShape(12.dp),
                    color = AstraLightCoral,
                    border = BorderStroke(1.dp, AstraCoralPrimary.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Business,
                            contentDescription = null,
                            tint = AstraDarkCoral,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$selectedDealName ▾",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = AstraDarkCoral,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                DropdownMenu(
                    expanded = showDealMenu,
                    onDismissRequest = { showDealMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Acme Financial ($450K)", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        onClick = {
                            selectedDealName = "Acme Financial"
                            showDealMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Apex Global ($280K)", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        onClick = {
                            selectedDealName = "Apex Global"
                            showDealMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Nova Health ($600K)", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        onClick = {
                            selectedDealName = "Nova Health"
                            showDealMenu = false
                        }
                    )
                }
            }
        }

        // Header Title
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.History,
                    contentDescription = null,
                    tint = AstraCoralPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = "Deal Time Machine",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = "See how ASTRA's understanding evolved over time.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Main Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Interactive Time Machine Slider Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TIMELINE SLIDER",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AstraCoralPrimary,
                            letterSpacing = 0.5.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Surface(
                            shape = CircleShape,
                            color = AstraLightCoral
                        ) {
                            Text(
                                text = currentStep.stageTitle,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = AstraDarkCoral,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Slider Component
                    Slider(
                        value = selectedStepIndex.toFloat(),
                        onValueChange = { selectedStepIndex = it.toInt() },
                        valueRange = 0f..3f,
                        steps = 2,
                        colors = SliderDefaults.colors(
                            thumbColor = AstraCoralPrimary,
                            activeTrackColor = AstraCoralPrimary,
                            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )

                    // Step Label Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        steps.forEachIndexed { idx, step ->
                            val isSelected = idx == selectedStepIndex
                            Surface(
                                modifier = Modifier.clickable { selectedStepIndex = idx },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) AstraCoralPrimary else Color.Transparent
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Day ${step.dayNumber}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = step.stageTitle.split(" — ").getOrElse(1) { "" },
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 9.sp,
                                        color = if (isSelected) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Dynamic State Card: "ASTRA KNEW" & "What changed?"
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // ASTRA KNEW
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = AstraCoralPrimary
                            ) {
                                Box(
                                    modifier = Modifier.padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Psychology,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }

                            Text(
                                text = "ASTRA KNEW (Day ${currentStep.dayNumber})",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = AstraCoralPrimary,
                                fontSize = 11.sp,
                                letterSpacing = 0.5.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Text(
                            text = currentStep.astraKnew,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    // What changed?
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "WHAT CHANGED?",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AstraDarkCoral,
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = currentStep.whatChanged,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Memory Evolution Visual (Growing Node Network)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.4f)),
                border = BorderStroke(1.dp, AstraCoralPrimary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "MEMORY EVOLUTION VISUAL",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = AstraDarkCoral,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Node Network Row
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        itemsIndexed(currentStep.activeNodes) { index, nodeName ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = AstraCoralPrimary,
                                shadowElevation = 2.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.CheckCircle,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = nodeName,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            if (index < currentStep.activeNodes.size - 1) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                    contentDescription = null,
                                    tint = AstraDarkCoral,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "Network size: ${currentStep.activeNodes.size} core memory clusters active.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AstraDarkCoral
                    )
                }
            }

            // Evidence Drawer Action Button
            Button(
                onClick = { showEvidenceSheet = true },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Psychology,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "View supporting evidence (${currentStep.evidenceMemories.size}) →",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    // Evidence Bottom Sheet
    if (showEvidenceSheet) {
        EvidenceBottomSheet(
            title = "✦ Evidence at ${currentStep.stageTitle}",
            subtitle = "Supporting memory nodes captured on or before Day ${currentStep.dayNumber}",
            explanation = "At ${currentStep.stageTitle}, ASTRA grounded decisions on ${currentStep.evidenceMemories.size} key deal memories.",
            supportingMemories = currentStep.evidenceMemories,
            onDismiss = { showEvidenceSheet = false }
        )
    }
}
