package com.example.astra.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.astra.data.model.FollowUpResult
import com.example.astra.data.model.InteractionLog
import com.example.astra.ui.components.EnterpriseCard
import com.example.astra.ui.components.SectionHeader

@Composable
fun FollowUpStudioScreen(
    isGenerating: Boolean,
    followUpResult: FollowUpResult?,
    isSubmitting: Boolean,
    successMessage: String?,
    onGenerateFollowUp: (String) -> Unit,
    onClearFollowUp: () -> Unit,
    onLogInteraction: (InteractionLog) -> Unit,
    onClearSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var selectedStudioTab by remember { mutableIntStateOf(0) } // 0: AI Follow-Up, 1: Add Interaction (Retain)

    // Log Interaction form state
    var company by remember { mutableStateOf("Acme Financial") }
    var meetingType by remember { mutableStateOf("Negotiation & Pricing Review") }
    var date by remember { mutableStateOf("Today, 2:30 PM") }
    var participants by remember { mutableStateOf("Sarah Jenkins (CFO), Michael Chen (CTO)") }
    var customerConcerns by remember { mutableStateOf("Upfront annual billing cash flow constraint") }
    var objections by remember { mutableStateOf("Objected to full payment prior to pilot validation") }
    var approachUsed by remember { mutableStateOf("Proposed 90-day paid pilot with milestone KPI conversion") }
    var customerResponse by remember { mutableStateOf("Receptive and willing to submit for final approval") }
    var outcome by remember { mutableStateOf("Successful progression to final contract review") }
    var notes by remember { mutableStateOf("Follow up with TCO slide deck on Tuesday.") }
    var validationError by remember { mutableStateOf<String?>(null) }

    // Follow-up state
    var selectedStakeholder by remember { mutableStateOf("Sarah Jenkins") }
    val stakeholders = listOf("Sarah Jenkins (CFO)", "Michael Chen (CTO)", "David Miller (Procurement)")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Dashboard", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Rounded.Psychology, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "FOLLOW-UP & RETAIN",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Tab Selector
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                TabRow(
                    selectedTabIndex = selectedStudioTab,
                    containerColor = Color.Transparent,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = selectedStudioTab == 0,
                        onClick = { selectedStudioTab = 0 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Text("AI Follow-Up", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    )
                    Tab(
                        selected = selectedStudioTab == 1,
                        onClick = { selectedStudioTab = 1 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Text("Retain Interaction", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    )
                }
            }
        }

        if (selectedStudioTab == 0) {
            // AI Follow-Up Generator Tab
            item {
                EnterpriseCard(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Personalized Follow-Up",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Grounded in customer priorities, objections, and deal memories.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            item {
                SectionHeader(title = "Select Recipient", subtitle = "Choose decision maker")
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    stakeholders.forEach { stakeholder ->
                        val nameOnly = stakeholder.split(" ").take(2).joinToString(" ")
                        val isSelected = selectedStakeholder == nameOnly
                        Card(
                            onClick = { selectedStakeholder = nameOnly },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isSelected) Icons.Rounded.RadioButtonChecked else Icons.Rounded.RadioButtonUnchecked,
                                    contentDescription = null,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Column {
                                    Text(text = stakeholder, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(text = "Grounded in recent memory", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { onGenerateFollowUp(selectedStakeholder) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isGenerating
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Recalling Memories...", maxLines = 1, overflow = TextOverflow.Ellipsis)
                        } else {
                            Icon(Icons.Rounded.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Follow-Up", maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }

            if (followUpResult != null) {
                item {
                    SectionHeader(title = "Generated Communication", subtitle = "Tailored to ${followUpResult.recipient}")
                }

                item {
                    EnterpriseCard(
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "To: ${followUpResult.recipient} (${followUpResult.stakeholderRole})",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                TextButton(onClick = onClearFollowUp) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                        Text("Clear", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }

                            Text(
                                text = "Subject: ${followUpResult.subject}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                            Text(
                                text = followUpResult.body,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Grounded Episodic Memories (${followUpResult.groundedMemories.size}):",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            followUpResult.groundedMemories.forEach { mem ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "• $mem",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(10.dp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Retain Interaction Tab
            if (successMessage != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = successMessage,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Hindsight memory retained successfully.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            TextButton(onClick = onClearSuccess) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Text("Dismiss", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
            }

            if (validationError != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Rounded.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            Text(
                                text = validationError!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }

            item {
                EnterpriseCard {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Guided Interaction Logging & Retain",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Log customer meetings and objections to update the memory graph.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedTextField(
                            value = company,
                            onValueChange = { company = it },
                            label = { Text("Company / Deal Name *") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = meetingType,
                            onValueChange = { meetingType = it },
                            label = { Text("Meeting Type *") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = participants,
                            onValueChange = { participants = it },
                            label = { Text("Participants / Stakeholders *") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = objections,
                            onValueChange = { objections = it },
                            label = { Text("Objections Raised") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = approachUsed,
                            onValueChange = { approachUsed = it },
                            label = { Text("Approach / Strategy Used *") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = customerResponse,
                            onValueChange = { customerResponse = it },
                            label = { Text("Customer Response *") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = outcome,
                            onValueChange = { outcome = it },
                            label = { Text("Outcome / Status *") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text("Additional Notes & Takeaways") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            minLines = 3
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                if (company.isBlank() || meetingType.isBlank() || participants.isBlank() || approachUsed.isBlank() || customerResponse.isBlank() || outcome.isBlank()) {
                                    validationError = "Please fill in all mandatory fields (*)."
                                } else {
                                    validationError = null
                                    onLogInteraction(
                                        InteractionLog(
                                            company = company,
                                            meetingType = meetingType,
                                            date = date,
                                            participants = participants,
                                            customerConcerns = customerConcerns,
                                            objections = objections,
                                            approachUsed = approachUsed,
                                            customerResponse = customerResponse,
                                            outcome = outcome,
                                            notes = notes
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isSubmitting
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isSubmitting) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Retaining Memory...", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                } else {
                                    Icon(Icons.Rounded.Memory, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Submit & Retain to ASTRA", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
