package com.example.astra.data.remote

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

data class RetainRequest(
    val content: String,
    val metadata: Map<String, String>? = null
)

data class RetainResponse(
    @field:Json(name = "memory_id") val memoryId: String? = null,
    val id: String? = null,
    val status: String? = null,
    val message: String? = null
)

data class RecallRequest(
    val query: String,
    @field:Json(name = "top_k") val topK: Int = 10,
    @field:Json(name = "deal_id") val dealId: String? = null
)

data class MemoryDto(
    val id: String? = null,
    @field:Json(name = "deal_id") val dealId: String? = null,
    val content: String,
    val category: String? = "EXPERIENCE",
    val timestamp: Long? = null,
    val importance: Int? = 4,
    val metadata: Map<String, String>? = null
)

data class RecallResponse(
    val memories: List<MemoryDto>? = null,
    val results: List<MemoryDto>? = null
)

data class ReflectRequest(
    val query: String,
    val context: String? = null
)

data class ReflectResponse(
    @field:Json(name = "reflection_id") val reflectionId: String? = null,
    val id: String? = null,
    val answer: String? = null,
    val insights: List<String>? = null,
    val recommendations: List<String>? = null,
    @field:Json(name = "evidence_items") val evidenceItems: List<String>? = null,
    @field:Json(name = "related_memories") val relatedMemories: List<String>? = null,
    val timestamp: Long? = null
)

interface HindsightApiService {
    @POST("v1/default/banks/{bank_id}/memories")
    suspend fun retainMemory(
        @Path("bank_id") bankId: String = "astra-sales-intelligence",
        @Body request: RetainRequest
    ): Response<RetainResponse>

    @POST("v1/default/banks/{bank_id}/memories/recall")
    suspend fun recallMemories(
        @Path("bank_id") bankId: String = "astra-sales-intelligence",
        @Body request: RecallRequest
    ): Response<RecallResponse>

    @POST("v1/default/banks/{bank_id}/reflect")
    suspend fun reflectOnDeal(
        @Path("bank_id") bankId: String = "astra-sales-intelligence",
        @Body request: ReflectRequest
    ): Response<ReflectResponse>
}
