package com.example.astra.data.model

enum class DealStatus {
    SUCCESS,
    FAILURE,
    IN_PROGRESS
}

data class Deal(
    val id: String,
    val title: String,
    val company: String,
    val stage: String,
    val value: Double,
    val closeDate: String,
    val healthScore: Int, // 0-100
    val status: DealStatus,
    val summary: String
)

data class Stakeholder(
    val id: String,
    val dealId: String,
    val name: String,
    val role: String, // e.g. "CFO", "CTO", "VP Sales", "Procurement"
    val sentiment: String, // e.g. "Positive", "Neutral", "Skeptical", "Champion"
    val influence: String, // "High", "Medium", "Low"
    val notes: String,
    val avatarUrl: String = ""
)

data class Interaction(
    val id: String,
    val dealId: String,
    val stakeholderId: String?,
    val type: String, // "MEETING", "CALL", "EMAIL", "NOTE"
    val timestamp: Long,
    val summary: String,
    val sentiment: String,
    val keyTakeaways: List<String>
)

data class Memory(
    val id: String,
    val dealId: String,
    val content: String,
    val category: String, // "OBJECTION", "REQUIREMENT", "DECISION", "COMPETITOR", "EXPERIENCE"
    val timestamp: Long,
    val importance: Int // 1-5
)

data class MeetingBrief(
    val id: String,
    val dealId: String,
    val title: String,
    val objective: String,
    val agenda: List<String>,
    val recommendedTalkingPoints: List<String>,
    val potentialObjections: List<String>,
    val stakeholderProfiles: List<String>
)

data class LearningPattern(
    val id: String,
    val pattern: String,
    val frequency: Int,
    val successRateImpact: Float, // e.g. +25.0f
    val recommendation: String,
    val category: String
)

data class ReflectResult(
    val dealId: String,
    val reflectionId: String,
    val insights: List<String>,
    val recommendations: List<String>,
    val timestamp: Long
)

data class AskAstraResult(
    val query: String,
    val answer: String,
    val evidenceItems: List<String>,
    val recommendations: List<String>,
    val relatedMemories: List<Memory>
)

data class TimelineEvent(
    val id: String,
    val memoryId: String,
    val stage: String,
    val timestamp: String,
    val stakeholder: String,
    val context: String,
    val action: String,
    val response: String,
    val outcome: String,
    val success: Boolean
)

data class InteractionLog(
    val company: String,
    val meetingType: String,
    val date: String,
    val participants: String,
    val customerConcerns: String,
    val objections: String,
    val approachUsed: String,
    val customerResponse: String,
    val outcome: String,
    val notes: String
)

data class FollowUpResult(
    val recipient: String,
    val stakeholderRole: String,
    val subject: String,
    val body: String,
    val groundedMemories: List<String>
)
