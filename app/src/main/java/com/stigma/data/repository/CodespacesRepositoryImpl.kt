package com.stigma.data.repository

import com.stigma.data.remote.GitHubApi
import com.stigma.domain.model.CodespacesUsage
import com.stigma.domain.repository.CodespacesRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CodespacesRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApi
) : CodespacesRepository {

    override suspend fun getUsage(token: String): Result<CodespacesUsage> {
        return runCatching {
            Timber.d("Fetching Codespaces usage from GitHub API")
            val dto = gitHubApi.getCodespacesUsage("Bearer $token")
            CodespacesUsage(
                totalMinutesUsed = dto.totalMinutesUsed,
                totalPaidMinutesUsed = dto.totalPaidMinutesUsed,
                includedMinutes = dto.includedMinutes,
                minutesUsedBreakdown = dto.minutesUsedBreakdown
            )
        }
    }
}
