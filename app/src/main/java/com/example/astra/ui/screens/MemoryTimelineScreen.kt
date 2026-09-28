package com.example.astra.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astra.data.model.TimelineEvent
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral
import com.example.astra.ui.theme.AstraNegativeRed

data class GraphNode(
    val id: String,
    val label: String,
    val category: String,
    val isSuccess: Boolean,
    val details: String,
    val xRatio: Float,
    val yRatio: Float
)

enum class OutcomeTagType {
    SUCCESSFUL, FAILED, PROGRESSED
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MemoryTimelineScreen(
    timelineEvents: List<TimelineEvent>,
    initialTab: Int = 0,
    onBack: () -> Unit,
    onNavigateToLog: () -> Unit,
    onNavigateToTimeMachine: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) } // 0 = Timeline, 1 = Graph, 2 = Impact
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedNodeId by remember { mutableStateOf("n_cfo_roi") }
    var showWhyDialog by remember { mutableStateOf(false) }

    val nodes = listOf(
        GraphNode("n_cfo", "Sarah (CFO)", "Stakeholder", true, "Primary financial blocker.", 0.2f, 0.25f),
        GraphNode("n_cfo_pricing", "Pricing Concern", "Category", false, "High upfront cost rejected.", 0.5f, 0.2f),
        GraphNode("n_cfo_discount", "Discount Push", "Failed", false, "15% discount attempt failed.", 0.8f, 0.15f),
        GraphNode("n_cfo_roi", "ROI 90-Day Pilot", "Successful", true, "90-day milestone pilot accepted!", 0.8f, 0.35f),

        GraphNode("n_cto", "Michael (CTO)", "Stakeholder", true, "Technical evaluation lead.", 0.2f, 0.6f),
        GraphNode("n_cto_sec", "Security Audit", "Category", true, "SOC2 Type II validation.", 0.5f, 0.6f),
        GraphNode("n_cto_soc2", "SOC2 Compliance", "Successful", true, "Sub-50ms vector memory passed!", 0.8f, 0.6f),

        GraphNode("n_proc", "David M (Procurement)", "Stakeholder", true, "Contract terms negotiator.", 0.2f, 0.85f),
        GraphNode("n_proc_terms", "Net-60 Terms", "Pending", false, "Pushed for Net-60 extension.", 0.5f, 0.85f),
        GraphNode("n_proc_net30", "Net-30 Settlement", "Successful", true, "Settled on Net-30 with tier.", 0.8f, 0.85f)
    )

    val edges = listOf(
        Pair("n_cfo", "n_cfo_pricing"),
        Pair("n_cfo_pricing", "n_cfo_discount"),
        Pair("n_cfo_pricing", "n_cfo_roi"),
        Pair("n_cto", "n_cto_sec"),
        Pair("n_cto_sec", "n_cto_soc2"),
        Pair("n_proc", "n_proc_terms"),
        Pair("n_proc_terms", "n_proc_net30")
    )

    val selectedNode = nodes.find { it.id == selectedNodeId } ?: nodes.first()

    // Determine event tag helper
    fun getEventTagType(event: TimelineEvent): OutcomeTagType {
        return when {
            event.outcome.contains("progress", ignoreCase = true) || event.stage.contains("progress", ignoreCase = true) -> OutcomeTagType.PROGRESSED
            !event.success || event.outcome.contains("fail", ignoreCase = true) || event.stage.contains("fail", ignoreCase = true) -> OutcomeTagType.FAILED
            else -> OutcomeTagType.SUCCESSFUL
        }
    }

    // Filter events
    val filteredEvents = remember(timelineEvents, selectedFilter) {
        when (selectedFilter) {
            "✓ Successful" -> timelineEvents.filter { getEventTagType(it) == OutcomeTagType.SUCCESSFUL }
            "× Failed" -> timelineEvents.filter { getEventTagType(it) == OutcomeTagType.FAILED }
            "→ Progressed" -> timelineEvents.filter { getEventTagType(it) == OutcomeTagType.PROGRESSED }
            else -> timelineEvents
        }
    }

    val totalExperiencesCount = if (timelineEvents.size >= 17) timelineEvents.size else 17

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Top Header Row
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
                    Text(
                        text = "Dashboard",
                        fontWeight = FontWeight.Bold,
                        color = AstraCoralPrimary,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Button(
                onClick = onNavigateToLog,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+ Log experience",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // 2. Title & Total Memory Badge Block
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Adaptive Sales Memory",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Episodic deal evolution & graph",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Total Memory Badge: 🧠 17 experiences
            Surface(
                shape = CircleShape,
                color = AstraCoralPrimary.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, AstraCoralPrimary.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "🧠 $totalExperiencesCount experiences",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = AstraCoralPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Deal Time Machine Entry Point Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, AstraCoralPrimary.copy(alpha = 0.4f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = AstraLightCoral
                    ) {
                        Box(modifier = Modifier.padding(6.dp), contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Rounded.History,
                                contentDescription = null,
                                tint = AstraDarkCoral,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Deal Time Machine",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Explore historical memory evolution",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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

        // 3. Segmented Tab Bar: [ Timeline ] [ Graph ] [ Impact ]
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            SegmentedButton(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AstraCoralPrimary,
                    activeContentColor = Color.White,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text("Timeline 📜", fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            SegmentedButton(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AstraCoralPrimary,
                    activeContentColor = Color.White,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text("Graph 🕸️", fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            SegmentedButton(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AstraCoralPrimary,
                    activeContentColor = Color.White,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text("Impact ⚡", fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }

        // Content Area per Tab
        when (selectedTab) {
            0 -> {
                // TIMELINE VIEW
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Filter Controls Bar
                    val filterOptions = listOf("All", "✓ Successful", "× Failed", "→ Progressed")
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        itemsIndexed(filterOptions) { _, option ->
                            val isSelected = selectedFilter == option
                            Surface(
                                shape = CircleShape,
                                color = if (isSelected) AstraCoralPrimary else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                                modifier = Modifier.clickable { selectedFilter = option }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = option,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }

                    // Timeline Items
                    if (filteredEvents.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No experiences match the selected filter.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 96.dp)
                        ) {
                            itemsIndexed(filteredEvents) { index, event ->
                                TimelineEventItemRow(
                                    event = event,
                                    index = index,
                                    totalCount = filteredEvents.size,
                                    tagType = getEventTagType(event)
                                )
                            }
                        }
                    }
                }
            }
            1 -> {
                // VISUAL GRAPH VIEW
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Relationship Memory Graph",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                    ) {
                        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                            val containerWidth = maxWidth
                            val containerHeight = maxHeight

                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                edges.forEach { (srcId, dstId) ->
                                    val srcNode = nodes.find { it.id == srcId }
                                    val dstNode = nodes.find { it.id == dstId }
                                    if (srcNode != null && dstNode != null) {
                                        val start = Offset(srcNode.xRatio * w, srcNode.yRatio * h)
                                        val end = Offset(dstNode.xRatio * w, dstNode.yRatio * h)
                                        drawLine(
                                            color = if (dstNode.isSuccess) AstraCoralPrimary else AstraDarkCoral.copy(alpha = 0.4f),
                                            start = start,
                                            end = end,
                                            strokeWidth = 3f
                                        )
                                    }
                                }
                            }

                            nodes.forEach { node ->
                                val isSelected = node.id == selectedNodeId
                                val nodeX = containerWidth * node.xRatio
                                val nodeY = containerHeight * node.yRatio

                                Box(
                                    modifier = Modifier
                                        .offset(
                                            x = nodeX - 45.dp,
                                            y = nodeY - 14.dp
                                        )
                                ) {
                                    Surface(
                                        modifier = Modifier.clickable { selectedNodeId = node.id },
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (isSelected) AstraCoralPrimary else if (node.isSuccess) AstraLightCoral else MaterialTheme.colorScheme.surfaceVariant,
                                        border = if (isSelected) BorderStroke(2.dp, AstraDarkCoral) else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = if (node.isSuccess) Icons.Rounded.CheckCircle else Icons.Rounded.Cancel,
                                                contentDescription = null,
                                                tint = if (isSelected) Color.White else if (node.isSuccess) AstraDarkCoral else AstraNegativeRed,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = node.label,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Color.White else AstraDarkCoral,
                                                fontSize = 10.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.4f)),
                        border = BorderStroke(1.dp, AstraCoralPrimary.copy(alpha = 0.3f))
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
                                    text = selectedNode.label,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = AstraDarkCoral,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Surface(
                                    shape = CircleShape,
                                    color = if (selectedNode.isSuccess) AstraCoralPrimary else AstraNegativeRed
                                ) {
                                    Text(
                                        text = if (selectedNode.isSuccess) "✓ Successful" else "× Failed",
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
                                text = selectedNode.details,
                                style = MaterialTheme.typography.bodyMedium,
                                color = AstraDarkCoral
                            )
                        }
                    }
                }
            }
            2 -> {
                // IMPACT COMPARISON VIEW
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Deal Performance & Memory Impact",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Left: WITHOUT MEMORY
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(320.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Surface(
                                        shape = CircleShape,
                                        color = AstraNegativeRed.copy(alpha = 0.15f)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(Icons.Rounded.Cancel, contentDescription = null, tint = AstraNegativeRed, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("WITHOUT MEMORY", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = AstraNegativeRed, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        }
                                    }

                                    Text(
                                        text = "22% Win Rate",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        text = "Generic CRM notes, rep context lost between calls.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text("❌ Forgets CFO objections", style = MaterialTheme.typography.labelSmall, color = AstraNegativeRed, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text("❌ Generic follow-up emails", style = MaterialTheme.typography.labelSmall, color = AstraNegativeRed, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text("❌ Zero grounded evidence", style = MaterialTheme.typography.labelSmall, color = AstraNegativeRed, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                        }

                        // Right: WITH ASTRA MEMORY
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(320.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.5f)),
                            border = BorderStroke(1.5.dp, AstraCoralPrimary)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Surface(
                                        shape = CircleShape,
                                        color = AstraCoralPrimary
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("WITH ASTRA", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        }
                                    }

                                    Text(
                                        text = "82% Win Rate",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = AstraDarkCoral,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        text = "100% grounded in $totalExperiencesCount deal memories.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AstraDarkCoral
                                    )

                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text("✓ Retains CFO objections", style = MaterialTheme.typography.labelSmall, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text("✓ Tailored follow-ups", style = MaterialTheme.typography.labelSmall, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text("✓ Autonomous steering", style = MaterialTheme.typography.labelSmall, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }

                                Button(
                                    onClick = { showWhyDialog = true },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary),
                                    contentPadding = PaddingValues(vertical = 6.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                        Text("Evidence 🧠", fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Memory Learning Curve", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text("$totalExperiencesCount experiences logged", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Text("90% Max Win Rate", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = AstraCoralPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }
    }

    if (showWhyDialog) {
        AlertDialog(
            onDismissRequest = { showWhyDialog = false },
            title = { Text("ASTRA Grounded Evidence ✦", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "ASTRA's 82% win rate is backed by $totalExperiencesCount Hindsight episodic memory nodes from past enterprise deals. In 14 CFO negotiations, proposing a 90-day pilot resolved upfront cash-flow objections.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = { showWhyDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("Got it", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TimelineEventItemRow(
    event: TimelineEvent,
    index: Int,
    totalCount: Int,
    tagType: OutcomeTagType
) {
    val isFirst = index == 0
    val isLast = index == totalCount - 1
    val nodeYOffset = 22.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Left Column for Timeline Line and Node
        Box(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            val lineThickness = 3.dp
            val activeLineColor = AstraCoralPrimary.copy(alpha = 0.5f)

            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2f
                val nodeYPx = nodeYOffset.toPx()
                val strokeWidthPx = lineThickness.toPx()

                val startY = if (isFirst) nodeYPx else 0f
                val endY = if (isLast) nodeYPx else size.height

                drawLine(
                    color = activeLineColor,
                    start = Offset(centerX, startY),
                    end = Offset(centerX, endY),
                    strokeWidth = strokeWidthPx
                )
            }

            // Node Circle
            Surface(
                modifier = Modifier
                    .padding(top = nodeYOffset - 14.dp)
                    .size(28.dp),
                shape = CircleShape,
                color = when (tagType) {
                    OutcomeTagType.SUCCESSFUL -> AstraCoralPrimary
                    OutcomeTagType.FAILED -> AstraNegativeRed
                    OutcomeTagType.PROGRESSED -> Color(0xFF1E88E5)
                },
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.background)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when (tagType) {
                            OutcomeTagType.SUCCESSFUL -> Icons.Rounded.Check
                            OutcomeTagType.FAILED -> Icons.Rounded.Close
                            OutcomeTagType.PROGRESSED -> Icons.AutoMirrored.Rounded.ArrowForward
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Right Card Content
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Top Header Row: Stage Name & Outcome Tag Pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.stage,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    OutcomeTagPill(tagType = tagType)
                }

                // Metadata Row: Timestamp, Stakeholder, Memory ID
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Timestamp
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = event.timestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Stakeholder Chip
                    Surface(
                        shape = CircleShape,
                        color = AstraCoralPrimary.copy(alpha = 0.1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Person,
                                contentDescription = null,
                                tint = AstraCoralPrimary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = event.stakeholder,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = AstraCoralPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Memory ID Badge
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Psychology,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = event.memoryId,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 10.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                )

                // Event Detail Blocks
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TimelineDetailBlock(
                        label = "CONTEXT",
                        text = event.context,
                        labelColor = AstraDarkCoral
                    )

                    TimelineDetailBlock(
                        label = "ACTION TAKEN",
                        text = event.action,
                        labelColor = AstraCoralPrimary
                    )

                    TimelineDetailBlock(
                        label = "CUSTOMER RESPONSE",
                        text = event.response,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    TimelineDetailBlock(
                        label = "OUTCOME & IMPACT",
                        text = event.outcome,
                        labelColor = when (tagType) {
                            OutcomeTagType.SUCCESSFUL -> AstraDarkCoral
                            OutcomeTagType.FAILED -> AstraNegativeRed
                            OutcomeTagType.PROGRESSED -> Color(0xFF1565C0)
                        },
                        isBold = true
                    )
                }
            }
        }
    }
}

@Composable
private fun OutcomeTagPill(tagType: OutcomeTagType) {
    val (bgColor, textColor, text) = when (tagType) {
        OutcomeTagType.SUCCESSFUL -> Triple(
            AstraLightCoral,
            AstraDarkCoral,
            "✓ Successful"
        )
        OutcomeTagType.FAILED -> Triple(
            AstraNegativeRed.copy(alpha = 0.15f),
            AstraNegativeRed,
            "× Failed"
        )
        OutcomeTagType.PROGRESSED -> Triple(
            Color(0xFFE3F2FD),
            Color(0xFF1565C0),
            "→ Progressed"
        )
    }

    Surface(
        shape = CircleShape,
        color = bgColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun TimelineDetailBlock(
    label: String,
    text: String,
    labelColor: Color,
    isBold: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = labelColor,
            fontSize = 9.sp,
            letterSpacing = 0.5.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isBold) FontWeight.SemiBold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
