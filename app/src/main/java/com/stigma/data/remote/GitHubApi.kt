package com.stigma.data.remote

import com.stigma.data.remote.dto.GitHubCodespacesUsageDto
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Retrofit interface for the GitHub REST API endpoints used by STIGMA.
 */
interface GitHubApi {

    /**
     * Returns Codespaces billing/allowance usage for the authenticated user.
     * Requires a personal access token with the `codespace` scope.
     */
    @GET("user/codespaces/billing")
    suspend fun getCodespacesUsage(
        @Header("Authorization") bearerToken: String
    ): GitHubCodespacesUsageDto
}
