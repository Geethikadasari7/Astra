package com.example.astra.data.repository

import com.example.astra.data.remote.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AstraRepositoryTest {

    private class FakeHindsightApiService : HindsightApiService {
        var shouldFail = false
        var retainCalled = false
        var recallCalled = false
        var reflectCalled = false
        var lastBankId: String? = null
        var lastContext: String? = null

        override suspend fun retainMemory(bankId: String, request: RetainRequest): Response<RetainResponse> {
            retainCalled = true
            lastBankId = bankId
            if (shouldFail) {
                return Response.error(500, "Error".toResponseBody("text/plain".toMediaTypeOrNull()))
            }
            return Response.success(RetainResponse(memoryId = "mem_test_99", status = "SUCCESS"))
        }

        override suspend fun recallMemories(bankId: String, request: RecallRequest): Response<RecallResponse> {
            recallCalled = true
            lastBankId = bankId
            if (shouldFail) {
                return Response.error(500, "Error".toResponseBody("text/plain".toMediaTypeOrNull()))
            }
            return Response.success(
                RecallResponse(
                    memories = listOf(
                        MemoryDto(
                            id = "mem_recall_01",
                            dealId = request.dealId ?: "deal_acme_01",
                            content = "Recalled memory about SOC2 compliance",
                            category = "REQUIREMENT",
                            timestamp = System.currentTimeMillis(),
                            importance = 5
                        )
                    )
                )
            )
        }

        override suspend fun reflectOnDeal(bankId: String, request: ReflectRequest): Response<ReflectResponse> {
            reflectCalled = true
            lastBankId = bankId
            lastContext = request.context
            if (shouldFail) {
                return Response.error(500, "Error".toResponseBody("text/plain".toMediaTypeOrNull()))
            }
            return Response.success(
                ReflectResponse(
                    reflectionId = "ref_test_01",
                    answer = "Reflected answer regarding Acme Financial",
                    insights = listOf("Test insight on deal"),
                    recommendations = listOf("Test recommendation"),
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    private lateinit var fakeApi: FakeHindsightApiService
    private lateinit var repository: AstraRepositoryImpl

    @Before
    fun setUp() {
        fakeApi = FakeHindsightApiService()
        repository = AstraRepositoryImpl(fakeApi)
    }

    @Test
    fun testGetDealsReturnsAcmeFinancial() = runTest {
        val deals = repository.getDeals().first()
        assertTrue(deals.isNotEmpty())
        val acmeDeal = deals.find { it.company == "Acme Financial" }
        assertNotNull(acmeDeal)
        assertEquals(450000.0, acmeDeal?.value ?: 0.0, 0.01)
        assertEquals(88, acmeDeal?.healthScore)
    }

    @Test
    fun testGetStakeholdersContainsCfoAndCto() = runTest {
        val stakeholders = repository.getStakeholders("deal_acme_01").first()
        assertTrue(stakeholders.size >= 4)
        val cfo = stakeholders.find { it.name == "Sarah Jenkins" }
        val cto = stakeholders.find { it.name == "Michael Chen" }
        assertNotNull(cfo)
        assertNotNull(cto)
        assertEquals("Chief Financial Officer (CFO)", cfo?.role)
        assertEquals("Chief Technology Officer (CTO)", cto?.role)
    }

    @Test
    fun testGetMemoriesContains17Experiences() = runTest {
        val memories = repository.getMemories("deal_acme_01").first()
        assertEquals(17, memories.size)
        // Verify Day 1, Day 7, Day 15, Day 23 historical memories exist
        assertTrue(memories.any { it.content.contains("Day 1") || it.content.contains("Discovery") })
        assertTrue(memories.any { it.content.contains("Day 7") || it.content.contains("Pricing Objection") })
        assertTrue(memories.any { it.content.contains("Day 15") || it.content.contains("Technical Validation") || it.content.contains("ROI") })
        assertTrue(memories.any { it.content.contains("Day 23") || it.content.contains("CFO Sign-off") })
    }

    @Test
    fun testGetTimelineEventsReturnsValidSequence() = runTest {
        val events = repository.getTimelineEvents("deal_acme_01").first()
        assertTrue(events.isNotEmpty())
        assertTrue(events.any { it.stage == "Discovery" })
        assertTrue(events.any { it.outcome.contains("Progressed", ignoreCase = true) || it.stage.contains("Progressed", ignoreCase = true) || it.stage.contains("Sign-off", ignoreCase = true) })
        assertTrue(events.any { !it.success })
    }

    @Test
    fun testRetainMemorySuccess() = runTest {
        fakeApi.shouldFail = false
        val result = repository.retainMemory("deal_acme_01", "Test objection regarding security audit.", "OBJECTION")
        assertTrue(result.isSuccess)
        assertTrue(fakeApi.retainCalled)
        assertEquals("astra-sales-intelligence", fakeApi.lastBankId)
        val memory = result.getOrNull()
        assertNotNull(memory)
        assertEquals("mem_test_99", memory?.id)
    }

    @Test
    fun testRetainMemoryFallbackOnFailure() = runTest {
        fakeApi.shouldFail = true
        val result = repository.retainMemory("deal_acme_01", "Test objection regarding security audit.", "OBJECTION")
        assertTrue(result.isSuccess)
        val memory = result.getOrNull()
        assertNotNull(memory)
        assertEquals("Test objection regarding security audit.", memory?.content)
    }

    @Test
    fun testRecallMemoriesApiSuccess() = runTest {
        fakeApi.shouldFail = false
        val result = repository.recallMemories("SOC2", "deal_acme_01")
        assertTrue(result.isSuccess)
        assertTrue(fakeApi.recallCalled)
        assertEquals("astra-sales-intelligence", fakeApi.lastBankId)
        val memories = result.getOrNull()
        assertNotNull(memories)
        assertTrue(memories!!.any { it.content.contains("SOC2") })
    }

    @Test
    fun testReflectOnDealApiSuccessAndContextBinding() = runTest {
        fakeApi.shouldFail = false
        val result = repository.reflectOnDeal("deal_acme_01")
        assertTrue(result.isSuccess)
        assertTrue(fakeApi.reflectCalled)
        assertEquals("astra-sales-intelligence", fakeApi.lastBankId)
        assertNotNull(fakeApi.lastContext)
        assertTrue(fakeApi.lastContext!!.contains("Acme Financial"))
        assertTrue(fakeApi.lastContext!!.contains("Sarah Jenkins"))
        assertTrue(fakeApi.lastContext!!.contains("Geethika Dasari"))
        val reflection = result.getOrNull()
        assertNotNull(reflection)
        assertEquals("deal_acme_01", reflection?.dealId)
        assertTrue(reflection!!.insights.contains("Test insight on deal"))
    }

    @Test
    fun testAskAstraSuccessAndFallback() = runTest {
        fakeApi.shouldFail = false
        val result = repository.askAstra("Why did CFO Sarah reject discount?", "deal_acme_01")
        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertNotNull(data)
        assertEquals("Why did CFO Sarah reject discount?", data?.query)
        assertTrue(data!!.answer.isNotBlank())
        assertTrue(data.evidenceItems.isNotEmpty())
        assertTrue(data.recommendations.isNotEmpty())
    }
}
