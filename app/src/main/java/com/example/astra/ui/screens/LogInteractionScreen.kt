package com.example.astra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.astra.data.model.InteractionLog
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral

@Composable
fun LogInteractionScreen(
    isSubmitting: Boolean,
    successMessage: String?,
    onLogInteraction: (InteractionLog) -> Unit,
    onClearSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var whatHappened by remember { mutableStateOf("Sarah CFO agreed to 90-day pilot if technical approval is granted by CTO Michael.") }
    var selectedStakeholder by remember { mutableStateOf("Sarah Jenkins (CFO)") }
    var selectedOutcome by remember { mutableStateOf("Positive") } // Positive, Neutral, Negative

    val stakeholders = listOf("Sarah Jenkins (CFO)", "Michael Chen (CTO)", "David Miller (Procurement)")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
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
                        text = "Add Experience",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = AstraDarkCoral,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        if (successMessage != null) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AstraLightCoral),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = AstraDarkCoral)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = successMessage, fontWeight = FontWeight.Bold, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(text = "Memory graph updated.", style = MaterialTheme.typography.bodySmall, color = AstraDarkCoral, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    TextButton(onClick = onClearSuccess) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text("OK", color = AstraDarkCoral, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }

        // Multi-Step Conversational Experience Flow
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Step 1: What happened?
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "1. What happened?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    OutlinedTextField(
                        value = whatHappened,
                        onValueChange = { whatHappened = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        minLines = 3,
                        placeholder = { Text("Log key objection, preference, or outcome...") }
                    )
                }
            }

            // Step 2: Stakeholder Chips
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "2. Select Stakeholder",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        stakeholders.forEach { name ->
                            val isSelected = selectedStakeholder == name
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedStakeholder = name },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) AstraCoralPrimary else AstraLightCoral.copy(alpha = 0.4f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = name.split(" ").first() + " (" + name.split(" ").last() + ")",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else AstraDarkCoral,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Step 3: Outcome Buttons (Positive, Neutral, Negative)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "3. Meeting Outcome",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val outcomes = listOf(
                            Triple("Positive", "Positive 👍", AstraCoralPrimary),
                            Triple("Neutral", "Neutral 😐", AstraDarkCoral),
                            Triple("Negative", "Negative 👎", MaterialTheme.colorScheme.error)
                        )

                        outcomes.forEach { (key, label, color) ->
                            val isSelected = selectedOutcome == key
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedOutcome = key },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) color else AstraLightCoral.copy(alpha = 0.3f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else AstraDarkCoral,
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

        // Primary Action Button: "Add to memory 🧠"
        Button(
            onClick = {
                onLogInteraction(
                    InteractionLog(
                        company = "Acme Financial",
                        meetingType = "Negotiation",
                        date = "Today",
                        participants = selectedStakeholder,
                        customerConcerns = whatHappened,
                        objections = whatHappened,
                        approachUsed = "Interactive proposal",
                        customerResponse = selectedOutcome,
                        outcome = selectedOutcome,
                        notes = whatHappened
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AstraCoralPrimary,
                contentColor = Color.White
            ),
            enabled = !isSubmitting && whatHappened.isNotBlank()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Adding to Hindsight...", maxLines = 1, overflow = TextOverflow.Ellipsis)
                } else {
                    Text(
                        text = "Add to memory 🧠",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
