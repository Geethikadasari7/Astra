package com.example.astra.data.repository

import com.example.astra.data.model.*
import kotlinx.coroutines.flow.Flow

interface AstraRepository {
    fun getDeals(): Flow<List<Deal>>
    fun getDeal(dealId: String): Flow<Deal?>
    fun getStakeholders(dealId: String): Flow<List<Stakeholder>>
    fun getInteractions(dealId: String): Flow<List<Interaction>>
    fun getMemories(dealId: String): Flow<List<Memory>>
    fun getMeetingBriefs(dealId: String): Flow<List<MeetingBrief>>
    fun getLearningPatterns(): Flow<List<LearningPattern>>
    fun getTimelineEvents(dealId: String): Flow<List<TimelineEvent>>

    suspend fun retainMemory(
        dealId: String,
        content: String,
        category: String,
        metadata: Map<String, String>? = null
    ): Result<Memory>

    suspend fun recallMemories(query: String, dealId: String?): Result<List<Memory>>
    suspend fun reflectOnDeal(dealId: String, query: String? = null): Result<ReflectResult>
    suspend fun askAstra(query: String, dealId: String = "deal_acme_01"): Result<AskAstraResult>
    suspend fun logInteractionAndRetain(dealId: String, log: InteractionLog): Result<Memory>
    suspend fun generateFollowUp(dealId: String, stakeholderName: String): Result<FollowUpResult>
}
