package com.stigma.data.repository

import com.stigma.data.remote.GitHubApi
import com.stigma.data.remote.dto.GitHubCodespacesUsageDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CodespacesRepositoryImplTest {

    private lateinit var gitHubApi: GitHubApi
    private lateinit var repository: CodespacesRepositoryImpl

    @Before
    fun setUp() {
        gitHubApi = mock()
        repository = CodespacesRepositoryImpl(gitHubApi)
    }

    @Test
    fun `getUsage returns mapped domain model on success`() = runTest {
        val dto = GitHubCodespacesUsageDto(
            totalMinutesUsed = 120,
            totalPaidMinutesUsed = 0,
            includedMinutes = 2000,
            minutesUsedBreakdown = mapOf("UBUNTU" to 120)
        )
        whenever(gitHubApi.getCodespacesUsage("Bearer test-token")).thenReturn(dto)

        val result = repository.getUsage("test-token")

        assertTrue(result.isSuccess)
        val usage = result.getOrThrow()
        assertEquals(120, usage.totalMinutesUsed)
        assertEquals(0, usage.totalPaidMinutesUsed)
        assertEquals(2000, usage.includedMinutes)
        assertEquals(mapOf("UBUNTU" to 120), usage.minutesUsedBreakdown)
    }

    @Test
    fun `getUsage returns failure when api throws`() = runTest {
        whenever(gitHubApi.getCodespacesUsage("Bearer bad-token"))
            .thenThrow(RuntimeException("HTTP 401 Unauthorized"))

        val result = repository.getUsage("bad-token")

        assertTrue(result.isFailure)
        assertEquals("HTTP 401 Unauthorized", result.exceptionOrNull()?.message)
    }
}
