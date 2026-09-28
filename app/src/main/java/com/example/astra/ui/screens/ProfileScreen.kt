package com.example.astra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral

@Composable
fun ProfileScreen(
    isDemoModeActive: Boolean = true,
    onToggleDemoMode: (Boolean) -> Unit = {},
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    var autoRetain by remember { mutableStateOf(true) }
    var sub50msVectorSearch by remember { mutableStateOf(true) }
    var dealAlerts by remember { mutableStateOf(true) }
    var synthesisDigests by remember { mutableStateOf(true) }
    var biometricUnlock by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header Row
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = AstraCoralPrimary,
                            modifier = Modifier.size(18.dp)
                        )
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
                            text = "Profile & Settings",
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

        // Profile Hero Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(AstraCoralPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "GD",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Geethika Dasari",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Sales Representative • ASTRA Intelligence",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
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
                            Icon(Icons.Rounded.Psychology, contentDescription = null, tint = AstraDarkCoral, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "17 Memory Nodes Active",
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
        }

        // Quota Attainment Summary Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AstraLightCoral.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Q3 Quota Attainment",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = AstraDarkCoral
                        )
                        Text(
                            text = "$450,000 Negotiating • Target: $400,000",
                            style = MaterialTheme.typography.bodySmall,
                            color = AstraDarkCoral
                        )
                    }
                    Text(
                        text = "112%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = AstraDarkCoral
                    )
                }
            }
        }

        // Section Title: Settings & Configuration
        item {
            Text(
                text = "Settings & Preferences",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // 1. Personal Info Section
        item {
            SettingsCategoryCard(
                title = "Personal Info",
                icon = Icons.Rounded.Person
            ) {
                SettingsDetailRow(label = "Full Name", value = "Geethika Dasari")
                SettingsDetailRow(label = "Email", value = "geethikadasari@gmail.com")
                SettingsDetailRow(label = "Role", value = "Sales Representative")
                SettingsDetailRow(label = "Location & Time Zone", value = "India · IST (UTC+5:30)")
            }
        }

        // 2. Workspace & CRM
        item {
            SettingsCategoryCard(
                title = "Workspace & CRM",
                icon = Icons.Rounded.CorporateFare
            ) {
                SettingsDetailRow(label = "Organization", value = "ASTRA Intelligence")
                SettingsDetailRow(label = "CRM Integration", value = "Salesforce Connected ✓")
                SettingsDetailRow(label = "Calendar Sync", value = "Google Workspace Sync Active")
            }
        }

        // 3. Notifications Settings
        item {
            SettingsCategoryCard(
                title = "Notifications",
                icon = Icons.Rounded.Notifications
            ) {
                SettingsToggleRow(
                    label = "Deal Activity Alerts",
                    description = "Real-time updates on stakeholder shifts",
                    checked = dealAlerts,
                    onCheckedChange = { dealAlerts = it }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsToggleRow(
                    label = "Synthesis Digests",
                    description = "Weekly memory pattern summaries",
                    checked = synthesisDigests,
                    onCheckedChange = { synthesisDigests = it }
                )
            }
        }

        // 4. Memory Settings
        item {
            SettingsCategoryCard(
                title = "Memory Settings",
                icon = Icons.Rounded.Psychology
            ) {
                SettingsToggleRow(
                    label = "Auto-Retain Memories",
                    description = "Automatically extract & index meeting notes",
                    checked = autoRetain,
                    onCheckedChange = { autoRetain = it }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsToggleRow(
                    label = "Sub-50ms Vector Search",
                    description = "Enable local vector memory graph cache",
                    checked = sub50msVectorSearch,
                    onCheckedChange = { sub50msVectorSearch = it }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsDetailRow(label = "Active Memory Graph", value = "17 Hindsight Nodes")
            }
        }

        // 5. Appearance & Security
        item {
            SettingsCategoryCard(
                title = "Security & Privacy",
                icon = Icons.Rounded.Security
            ) {
                SettingsDetailRow(label = "Compliance Standard", value = "SOC2 Type II Certified")
                SettingsDetailRow(label = "Vector Encryption", value = "AES-256 Encrypted")
                SettingsToggleRow(
                    label = "Biometric Unlock",
                    description = "Require Face ID / Fingerprint to view deal memory",
                    checked = biometricUnlock,
                    onCheckedChange = { biometricUnlock = it }
                )
            }
        }

        // 6. Hackathon Demo Mode & Account
        item {
            SettingsCategoryCard(
                title = "Account & Demo Mode",
                icon = Icons.Rounded.Tune
            ) {
                SettingsToggleRow(
                    label = "Hackathon Demo Mode",
                    description = "Show 60s guided demo overlays across screens",
                    checked = isDemoModeActive,
                    onCheckedChange = onToggleDemoMode
                )
            }
        }

        // Sign Out Button
        item {
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Logout,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign Out",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsCategoryCard(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AstraCoralPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            content()
        }
    }
}

@Composable
private fun SettingsDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SettingsToggleRow(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AstraCoralPrimary
            )
        )
    }
}
