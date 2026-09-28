package com.example.astra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.astra.data.remote.HindsightClient
import com.example.astra.data.repository.AstraRepositoryImpl
import com.example.astra.ui.navigation.AstraScreen
import com.example.astra.ui.screens.*
import com.example.astra.ui.theme.*
import com.example.astra.ui.viewmodel.AstraViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val hindsightApi = HindsightClient.apiService
        val repository = AstraRepositoryImpl(hindsightApi)
        val viewModel = AstraViewModel(repository)

        setContent {
            AstraTheme {
                AstraMainApp(viewModel = viewModel)
            }
        }
    }
}

data class AstraBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: AstraScreen
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstraMainApp(viewModel: AstraViewModel) {
    var currentScreen by remember { mutableStateOf<AstraScreen>(AstraScreen.Landing) }

    var showSearchDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }

    val deal by viewModel.deal.collectAsState()
    val stakeholders by viewModel.stakeholders.collectAsState()
    val memories by viewModel.memories.collectAsState()
    val meetingBriefs by viewModel.meetingBriefs.collectAsState()
    val timelineEvents by viewModel.timelineEvents.collectAsState()
    val briefLoadingState by viewModel.briefLoadingState.collectAsState()
    val loadingProgressText by viewModel.loadingProgressText.collectAsState()
    val selectedRecommendation by viewModel.selectedRecommendation.collectAsState()
    val isSubmittingInteraction by viewModel.isSubmittingInteraction.collectAsState()
    val interactionSuccessMessage by viewModel.interactionSuccessMessage.collectAsState()
    val isGeneratingFollowUp by viewModel.isGeneratingFollowUp.collectAsState()
    val followUpResult by viewModel.followUpResult.collectAsState()
    val isDemoModeActive by viewModel.isDemoModeActive.collectAsState()
    val learningPatterns by viewModel.learningPatterns.collectAsState()
    val askAstraState by viewModel.askAstraState.collectAsState()

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val isWideScreen = adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

    val isFullScreenFlow = currentScreen is AstraScreen.Landing || currentScreen is AstraScreen.Onboarding || currentScreen is AstraScreen.Auth

    val topBar: @Composable () -> Unit = {
        TopAppBar(
            title = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "ASTRA",
                            fontWeight = FontWeight.ExtraBold,
                            color = AstraCoralPrimary,
                            fontSize = 20.sp
                        )
                        Surface(
                            shape = CircleShape,
                            color = AstraLightCoral
                        ) {
                            Text(
                                text = "Enterprise Intelligence",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = AstraDarkCoral,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                    Text(
                        text = "Adaptive Sales Memory",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 10.sp
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            ),
            actions = {
                IconButton(onClick = { showSearchDialog = true }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = { showNotificationsDialog = true }) {
                    BadgedBox(
                        badge = { Badge { Text("3") } }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                IconButton(onClick = { currentScreen = AstraScreen.Profile }) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(AstraCoralPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "GD",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        )
    }

    val screenContent = @Composable {
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                AstraScreen.Landing -> {
                    LandingScreen(
                        onEnterDashboard = { currentScreen = AstraScreen.Onboarding },
                        onEnterDemo = {
                            viewModel.toggleDemoMode(true)
                            currentScreen = AstraScreen.Dashboard
                        }
                    )
                }
                AstraScreen.Onboarding -> {
                    OnboardingScreen(
                        onCompleteOnboarding = { currentScreen = AstraScreen.Auth }
                    )
                }
                AstraScreen.Auth -> {
                    AuthScreen(
                        onAuthSuccess = { currentScreen = AstraScreen.Dashboard }
                    )
                }
                AstraScreen.Dashboard -> {
                    DashboardScreen(
                        deal = deal,
                        timelineEvents = timelineEvents,
                        askAstraState = askAstraState,
                        onAskAstraQuery = { query -> viewModel.askAstra(query) },
                        onClearAskAstra = { viewModel.clearAskAstra() },
                        onNavigateToDealDetail = { currentScreen = AstraScreen.DealDetail },
                        onNavigateToMeetingBrief = { currentScreen = AstraScreen.MeetingBrief },
                        onNavigateToTimeline = { currentScreen = AstraScreen.Memory },
                        onNavigateToLog = { currentScreen = AstraScreen.AddExperience },
                        onNavigateToFollowUp = { currentScreen = AstraScreen.FollowUpStudio },
                        onNavigateToImpact = { currentScreen = AstraScreen.Impact },
                        onNavigateToSettings = { currentScreen = AstraScreen.Profile }
                    )
                }
                AstraScreen.Deals -> {
                    DealsPageScreen(
                        deal = deal,
                        initialTab = 0,
                        onSelectDeal = { currentScreen = AstraScreen.DealDetail },
                        onBack = { currentScreen = AstraScreen.Dashboard }
                    )
                }
                AstraScreen.Accounts -> {
                    DealsPageScreen(
                        deal = deal,
                        initialTab = 1,
                        onSelectDeal = { currentScreen = AstraScreen.DealDetail },
                        onBack = { currentScreen = AstraScreen.Dashboard }
                    )
                }
                AstraScreen.DealDetail -> {
                    AcmeDealDetailScreen(
                        deal = deal,
                        stakeholders = stakeholders,
                        memories = memories,
                        timelineEvents = timelineEvents,
                        onShowWhy = { recId -> viewModel.showWhyForRecommendation(recId) },
                        onNavigateToTimeMachine = { currentScreen = AstraScreen.DealTimeMachine },
                        onBack = { currentScreen = AstraScreen.Deals }
                    )
                }
                AstraScreen.Memory, AstraScreen.MemoryTimeline -> {
                    MemoryTimelineScreen(
                        timelineEvents = timelineEvents,
                        initialTab = 0,
                        onBack = { currentScreen = AstraScreen.Dashboard },
                        onNavigateToLog = { currentScreen = AstraScreen.AddExperience },
                        onNavigateToTimeMachine = { currentScreen = AstraScreen.DealTimeMachine }
                    )
                }
                AstraScreen.Impact, AstraScreen.MemoryImpact -> {
                    MemoryTimelineScreen(
                        timelineEvents = timelineEvents,
                        initialTab = 2,
                        onBack = { currentScreen = AstraScreen.Dashboard },
                        onNavigateToLog = { currentScreen = AstraScreen.AddExperience },
                        onNavigateToTimeMachine = { currentScreen = AstraScreen.DealTimeMachine }
                    )
                }
                AstraScreen.DealTimeMachine -> {
                    DealTimeMachineScreen(
                        memories = memories,
                        onBack = { currentScreen = AstraScreen.Memory }
                    )
                }
                AstraScreen.MeetingPrep, AstraScreen.MeetingBrief -> {
                    AiMeetingBriefScreen(
                        meetingBrief = meetingBriefs.firstOrNull(),
                        briefLoadingState = briefLoadingState,
                        loadingProgressText = loadingProgressText,
                        onLoadBrief = { viewModel.loadAiMeetingBrief() },
                        onResetBrief = { viewModel.resetBriefLoading() },
                        onShowWhy = { recId -> viewModel.showWhyForRecommendation(recId) },
                        onBack = { currentScreen = AstraScreen.Dashboard }
                    )
                }
                AstraScreen.AddExperience, AstraScreen.LogInteraction -> {
                    LogInteractionScreen(
                        isSubmitting = isSubmittingInteraction,
                        successMessage = interactionSuccessMessage,
                        onLogInteraction = { log -> viewModel.logInteraction(log) {} },
                        onClearSuccess = { viewModel.clearInteractionSuccessMessage() },
                        onBack = { currentScreen = AstraScreen.Dashboard }
                    )
                }
                AstraScreen.FollowUpStudio -> {
                    FollowUpStudioScreen(
                        isGenerating = isGeneratingFollowUp,
                        followUpResult = followUpResult,
                        isSubmitting = isSubmittingInteraction,
                        successMessage = interactionSuccessMessage,
                        onGenerateFollowUp = { name -> viewModel.generateFollowUpForStakeholder(name) },
                        onClearFollowUp = { viewModel.clearFollowUp() },
                        onLogInteraction = { log -> viewModel.logInteraction(log) {} },
                        onClearSuccess = { viewModel.clearInteractionSuccessMessage() },
                        onBack = { currentScreen = AstraScreen.Dashboard }
                    )
                }
                AstraScreen.Insights -> {
                    InsightsScreen(
                        patterns = learningPatterns,
                        initialTab = 0,
                        onBack = { currentScreen = AstraScreen.Dashboard }
                    )
                }
                AstraScreen.Settings, AstraScreen.Profile -> {
                    ProfileScreen(
                        isDemoModeActive = isDemoModeActive,
                        onToggleDemoMode = { viewModel.toggleDemoMode(it) },
                        onBack = { currentScreen = AstraScreen.Dashboard },
                        onLogout = { currentScreen = AstraScreen.Landing }
                    )
                }
            }

            if (selectedRecommendation != null) {
                WhyMemoryDialog(
                    recommendation = selectedRecommendation!!,
                    onDismiss = { viewModel.dismissWhyDialog() }
                )
            }

            if (showSearchDialog) {
                var searchQuery by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { showSearchDialog = false },
                    title = { Text("Search Memory & Deals 🔍", fontWeight = FontWeight.Bold) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search CFO objections, SOC2, deals...") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )
                            Text("Sub-50ms vector search across 17 memory nodes.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showSearchDialog = false
                                if (searchQuery.isNotBlank()) {
                                    viewModel.askAstra(searchQuery)
                                    currentScreen = AstraScreen.Dashboard
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary)
                        ) {
                            Text("Ask ASTRA ✦")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showSearchDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }

            if (showNotificationsDialog) {
                AlertDialog(
                    onDismissRequest = { showNotificationsDialog = false },
                    title = { Text("Notifications 🔔", fontWeight = FontWeight.Bold) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Surface(shape = RoundedCornerShape(10.dp), color = AstraLightCoral.copy(alpha = 0.5f)) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("Sarah CFO milestone update", fontWeight = FontWeight.Bold, color = AstraDarkCoral)
                                    Text("90-day pilot recommendation generated.", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Surface(shape = RoundedCornerShape(10.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("David CTO vector check", fontWeight = FontWeight.Bold)
                                    Text("Sub-50ms local memory test verified.", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showNotificationsDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = AstraCoralPrimary)
                        ) {
                            Text("Done")
                        }
                    }
                )
            }
        }
    }

    if (isFullScreenFlow) {
        screenContent()
    } else {
        if (isWideScreen) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                NavigationRail(
                    modifier = Modifier.width(240.dp),
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "ASTRA 🧠",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AstraCoralPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    NavigationRailItem(
                        selected = currentScreen is AstraScreen.Dashboard,
                        onClick = { currentScreen = AstraScreen.Dashboard },
                        icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
                        label = { Text("Home") },
                        colors = NavigationRailItemDefaults.colors(selectedIconColor = AstraCoralPrimary, indicatorColor = AstraLightCoral)
                    )
                    NavigationRailItem(
                        selected = currentScreen is AstraScreen.Deals || currentScreen is AstraScreen.DealDetail || currentScreen is AstraScreen.Accounts,
                        onClick = { currentScreen = AstraScreen.Deals },
                        icon = { Icon(Icons.Rounded.BusinessCenter, contentDescription = null) },
                        label = { Text("Deals") },
                        colors = NavigationRailItemDefaults.colors(selectedIconColor = AstraCoralPrimary, indicatorColor = AstraLightCoral)
                    )
                    NavigationRailItem(
                        selected = currentScreen is AstraScreen.Memory || currentScreen is AstraScreen.MemoryTimeline || currentScreen is AstraScreen.Impact || currentScreen is AstraScreen.MemoryImpact || currentScreen is AstraScreen.DealTimeMachine,
                        onClick = { currentScreen = AstraScreen.Memory },
                        icon = { Icon(Icons.Rounded.Psychology, contentDescription = null) },
                        label = { Text("Memory") },
                        colors = NavigationRailItemDefaults.colors(selectedIconColor = AstraCoralPrimary, indicatorColor = AstraLightCoral)
                    )
                    NavigationRailItem(
                        selected = currentScreen is AstraScreen.Insights,
                        onClick = { currentScreen = AstraScreen.Insights },
                        icon = { Icon(Icons.Rounded.AutoAwesome, contentDescription = null) },
                        label = { Text("Insights") },
                        colors = NavigationRailItemDefaults.colors(selectedIconColor = AstraCoralPrimary, indicatorColor = AstraLightCoral)
                    )
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    topBar = topBar
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        screenContent()
                    }
                }
            }
        } else {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                contentWindowInsets = WindowInsets.safeDrawing,
                topBar = topBar,
                bottomBar = {
                    AstraFloatingBottomBar(
                        currentScreen = currentScreen,
                        onNavigate = { screen -> currentScreen = screen }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    screenContent()
                }
            }
        }
    }
}

@Composable
fun AstraFloatingBottomBar(
    currentScreen: AstraScreen,
    onNavigate: (AstraScreen) -> Unit
) {
    val items = listOf(
        AstraBottomNavItem("Home", Icons.Rounded.Home, AstraScreen.Dashboard),
        AstraBottomNavItem("Deals", Icons.Rounded.BusinessCenter, AstraScreen.Deals),
        AstraBottomNavItem("Memory", Icons.Rounded.Psychology, AstraScreen.Memory),
        AstraBottomNavItem("Insights", Icons.Rounded.AutoAwesome, AstraScreen.Insights)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, AstraBorderLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = when (item.screen) {
                    AstraScreen.Dashboard -> currentScreen is AstraScreen.Dashboard
                    AstraScreen.Deals -> currentScreen is AstraScreen.Deals || currentScreen is AstraScreen.DealDetail || currentScreen is AstraScreen.Accounts
                    AstraScreen.Memory -> currentScreen is AstraScreen.Memory || currentScreen is AstraScreen.MemoryTimeline || currentScreen is AstraScreen.Impact || currentScreen is AstraScreen.MemoryImpact || currentScreen is AstraScreen.DealTimeMachine
                    AstraScreen.Insights -> currentScreen is AstraScreen.Insights
                    else -> false
                }

                val contentColor = if (isSelected) AstraCoralPrimary else Color(0xFF505050)

                Surface(
                    onClick = { onNavigate(item.screen) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) AstraLightCoral.copy(alpha = 0.5f) else Color.Transparent,
                    modifier = Modifier.padding(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = contentColor,
                            modifier = Modifier.size(22.dp)
                        )
                        if (isSelected) {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = contentColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=891dp,dpi=420")
@Composable
fun AstraAppPreview() {
    AstraTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Astra Enterprise Sales Intelligence Navigation Redesign Preview")
            }
        }
    }
}
