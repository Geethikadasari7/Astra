package com.example.astra.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astra.data.model.*
import com.example.astra.data.repository.AstraRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class RecommendationWithWhy(
    val id: String,
    val title: String,
    val description: String,
    val supportingMemories: List<Memory>,
    val reflectionSummary: String
)

sealed class AskAstraUiState {
    object Idle : AskAstraUiState()
    data class Loading(val progressText: String) : AskAstraUiState()
    data class Success(val result: AskAstraResult) : AskAstraUiState()
    data class Error(val message: String) : AskAstraUiState()
}

class AstraViewModel(
    private val repository: AstraRepository
) : ViewModel() {

    private val _selectedDealId = MutableStateFlow("deal_acme_01")
    val selectedDealId: StateFlow<String> = _selectedDealId.asStateFlow()

    val deal: StateFlow<Deal?> = _selectedDealId
        .flatMapLatest { repository.getDeal(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val stakeholders: StateFlow<List<Stakeholder>> = _selectedDealId
        .flatMapLatest { repository.getStakeholders(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val memories: StateFlow<List<Memory>> = _selectedDealId
        .flatMapLatest { repository.getMemories(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val interactions: StateFlow<List<Interaction>> = _selectedDealId
        .flatMapLatest { repository.getInteractions(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val meetingBriefs: StateFlow<List<MeetingBrief>> = _selectedDealId
        .flatMapLatest { repository.getMeetingBriefs(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val learningPatterns: StateFlow<List<LearningPattern>> = repository.getLearningPatterns()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val timelineEvents: StateFlow<List<TimelineEvent>> = _selectedDealId
        .flatMapLatest { repository.getTimelineEvents(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // AI Meeting Brief loading progress state
    private val _briefLoadingState = MutableStateFlow(BriefLoadingState.IDLE)
    val briefLoadingState: StateFlow<BriefLoadingState> = _briefLoadingState.asStateFlow()

    private val _loadingProgressText = MutableStateFlow("")
    val loadingProgressText: StateFlow<String> = _loadingProgressText.asStateFlow()

    // Ask ASTRA Console state
    private val _askAstraState = MutableStateFlow<AskAstraUiState>(AskAstraUiState.Idle)
    val askAstraState: StateFlow<AskAstraUiState> = _askAstraState.asStateFlow()

    // Why? Modal / Sheet state
    private val _selectedRecommendation = MutableStateFlow<RecommendationWithWhy?>(null)
    val selectedRecommendation: StateFlow<RecommendationWithWhy?> = _selectedRecommendation.asStateFlow()

    // Interaction form submission state
    private val _isSubmittingInteraction = MutableStateFlow(false)
    val isSubmittingInteraction: StateFlow<Boolean> = _isSubmittingInteraction.asStateFlow()

    private val _interactionSuccessMessage = MutableStateFlow<String?>(null)
    val interactionSuccessMessage: StateFlow<String?> = _interactionSuccessMessage.asStateFlow()

    // Follow-up generator state
    private val _isGeneratingFollowUp = MutableStateFlow(false)
    val isGeneratingFollowUp: StateFlow<Boolean> = _isGeneratingFollowUp.asStateFlow()

    private val _followUpResult = MutableStateFlow<FollowUpResult?>(null)
    val followUpResult: StateFlow<FollowUpResult?> = _followUpResult.asStateFlow()

    // Demo Mode state
    private val _isDemoModeActive = MutableStateFlow(false)
    val isDemoModeActive: StateFlow<Boolean> = _isDemoModeActive.asStateFlow()

    private val _currentDemoStep = MutableStateFlow(0)
    val currentDemoStep: StateFlow<Int> = _currentDemoStep.asStateFlow()

    val demoSteps = listOf(
        "1. Acme Financial Overview ($450K Negotiation, Sarah CFO, Michael CTO)",
        "2. Interactive Memory Timeline (Discovery -> Objections -> Technical Validation)",
        "3. Log New Interaction & Retain to Hindsight Memory",
        "4. AI Follow-Up Generator Grounded in Customer Priorities",
        "5. Memory Impact & Learning Curve (0 to 15+ Experiences)"
    )

    fun loadAiMeetingBrief() {
        viewModelScope.launch {
            _briefLoadingState.value = BriefLoadingState.LOADING
            _loadingProgressText.value = "✦ ASTRA is recalling relevant experiences..."
            delay(800)
            _loadingProgressText.value = "✦ ASTRA is connecting the dots..."
            delay(800)
            _loadingProgressText.value = "✦ ASTRA is preparing your briefing..."
            delay(600)
            
            repository.reflectOnDeal(_selectedDealId.value)
            _briefLoadingState.value = BriefLoadingState.LOADED
        }
    }

    fun resetBriefLoading() {
        _briefLoadingState.value = BriefLoadingState.IDLE
    }

    fun askAstra(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _askAstraState.value = AskAstraUiState.Loading("✦ ASTRA is recalling relevant experiences...")
            delay(800)
            _askAstraState.value = AskAstraUiState.Loading("✦ ASTRA is connecting the dots...")
            delay(800)
            _askAstraState.value = AskAstraUiState.Loading("✦ ASTRA is preparing your briefing...")
            delay(600)

            val result = repository.askAstra(query, _selectedDealId.value)
            if (result.isSuccess) {
                _askAstraState.value = AskAstraUiState.Success(result.getOrThrow())
            } else {
                _askAstraState.value = AskAstraUiState.Error("ASTRA couldn't access its memory right now. Please try again.")
            }
        }
    }

    fun clearAskAstra() {
        _askAstraState.value = AskAstraUiState.Idle
    }

    fun logInteraction(log: InteractionLog, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isSubmittingInteraction.value = true
            delay(800)
            val result = repository.logInteractionAndRetain(_selectedDealId.value, log)
            _isSubmittingInteraction.value = false
            if (result.isSuccess) {
                _interactionSuccessMessage.value = "🧠 Experience retained in Hindsight Cloud"
                onComplete()
            }
        }
    }

    fun clearInteractionSuccessMessage() {
        _interactionSuccessMessage.value = null
    }

    fun generateFollowUpForStakeholder(stakeholderName: String) {
        viewModelScope.launch {
            _isGeneratingFollowUp.value = true
            delay(1000)
            val result = repository.generateFollowUp(_selectedDealId.value, stakeholderName)
            _isGeneratingFollowUp.value = false
            if (result.isSuccess) {
                _followUpResult.value = result.getOrThrow()
            }
        }
    }

    fun clearFollowUp() {
        _followUpResult.value = null
    }

    fun showWhyForRecommendation(recId: String) {
        val currentMemories = memories.value
        val rec = when (recId) {
            "rec_01" -> RecommendationWithWhy(
                id = "rec_01",
                title = "Geethika, consider leading this conversation with quantified ROI",
                description = "Geethika, Sarah Jenkins has expressed hesitation regarding upfront annual commitment. Structure a 90-day milestone-based pilot.",
                supportingMemories = listOf(
                    currentMemories.find { it.id == "mem_01" } ?: Memory("mem_01", "deal_acme_01", "CFO Sarah Jenkins objected to upfront annual billing without a risk-free 90-day pilot phase.", "OBJECTION", 0L, 5),
                    currentMemories.find { it.id == "mem_04" } ?: Memory("mem_04", "deal_acme_01", "Decision made to structure contract as a 90-day paid pilot with automatic conversion upon hitting KPI targets.", "DECISION", 0L, 5)
                ),
                reflectionSummary = "Reflection: In 14 previous enterprise software negotiations, offering a structured 90-day paid pilot increased close rate by 28.5% when CFO-level budget freezes were active."
            )
            "rec_02" -> RecommendationWithWhy(
                id = "rec_02",
                title = "Highlight sub-50ms vector memory retrieval for Michael Chen (CTO)",
                description = "Michael Chen prioritizes low latency and architectural robustness. Lead with local-first vector performance.",
                supportingMemories = listOf(
                    currentMemories.find { it.id == "mem_02" } ?: Memory("mem_02", "deal_acme_01", "CTO Michael Chen confirmed SOC2 Type II certification is a non-negotiable blocker.", "REQUIREMENT", 0L, 5),
                    currentMemories.find { it.id == "mem_08" } ?: Memory("mem_08", "deal_acme_01", "Michael Chen praised the robust Kotlin Coroutines concurrency model and clean architecture.", "REQUIREMENT", 0L, 3),
                    currentMemories.find { it.id == "mem_09" } ?: Memory("mem_09", "deal_acme_01", "ROI discussion confirmed 4 hours daily saved per rep.", "REQUIREMENT", 0L, 4)
                ),
                reflectionSummary = "Reflection: Technical validation by CTOs who test concurrency models early accelerates technical sign-off by 22 days."
            )
            else -> RecommendationWithWhy(
                id = recId,
                title = "Address Net-60 Payment Terms with David Miller (Procurement)",
                description = "David Miller is pushing for net-60 terms. Offer standard net-30 with an early-settlement incentive.",
                supportingMemories = listOf(
                    currentMemories.find { it.id == "mem_10" } ?: Memory("mem_10", "deal_acme_01", "David Miller requested net-60 payment terms instead of net-30.", "OBJECTION", 0L, 3),
                    currentMemories.find { it.id == "mem_11" } ?: Memory("mem_11", "deal_acme_01", "Successfully handled pricing pushback by offering phased rollout tiers.", "EXPERIENCE", 0L, 4)
                ),
                reflectionSummary = "Reflection: Conceding payment terms only in exchange for multi-year contract expansion maintains average deal value."
            )
        }
        _selectedRecommendation.value = rec
    }

    fun dismissWhyDialog() {
        _selectedRecommendation.value = null
    }

    fun toggleDemoMode(active: Boolean) {
        _isDemoModeActive.value = active
        if (active) {
            _currentDemoStep.value = 0
        }
    }

    fun nextDemoStep() {
        if (_currentDemoStep.value < demoSteps.size - 1) {
            _currentDemoStep.value += 1
        } else {
            _isDemoModeActive.value = false
        }
    }

    fun prevDemoStep() {
        if (_currentDemoStep.value > 0) {
            _currentDemoStep.value -= 1
        }
    }
}

enum class BriefLoadingState {
    IDLE,
    LOADING,
    LOADED
}
