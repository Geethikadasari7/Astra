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
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.astra.ui.theme.AstraCoralPrimary
import com.example.astra.ui.theme.AstraDarkCoral
import com.example.astra.ui.theme.AstraLightCoral

data class AccountItem(
    val name: String,
    val industry: String,
    val activeDeals: Int,
    val totalValue: String,
    val memoryNodes: Int
)

@Composable
fun AccountsScreen(
    onSelectAccount: (String) -> Unit,
    onBack: () -> Unit
) {
    val accounts = listOf(
        AccountItem("Acme Financial", "Banking & FinTech", 1, "$450,000", 17),
        AccountItem("Nexus Technologies", "Enterprise SaaS", 2, "$320,000", 12),
        AccountItem("Global Logistics Corp", "Supply Chain", 1, "$180,000", 8),
        AccountItem("Apex Healthcare", "Medical Devices", 1, "$290,000", 9)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
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
                        text = "${accounts.size} Key Accounts",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = AstraDarkCoral,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        item {
            Text(
                text = "Target Accounts",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(accounts) { acc ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectAccount(acc.name) },
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
                            Text(acc.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("${acc.industry} • ${acc.totalValue}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
