package com.stigma.domain.repository

import com.stigma.domain.model.CodespacesUsage

/**
 * Repository interface for fetching GitHub Codespaces billing/usage data.
 */
interface CodespacesRepository {
    /**
     * Returns the authenticated user's Codespaces allowance usage for the current billing cycle.
     */
    suspend fun getUsage(token: String): Result<CodespacesUsage>
}
