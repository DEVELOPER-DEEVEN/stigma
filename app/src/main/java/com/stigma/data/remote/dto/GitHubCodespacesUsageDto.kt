package com.stigma.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO for the GitHub Codespaces billing endpoint response.
 * Maps to GET /user/codespaces/billing
 */
data class GitHubCodespacesUsageDto(
    @SerializedName("total_minutes_used")
    val totalMinutesUsed: Int,
    @SerializedName("total_paid_minutes_used")
    val totalPaidMinutesUsed: Int,
    @SerializedName("included_minutes")
    val includedMinutes: Int,
    @SerializedName("minutes_used_breakdown")
    val minutesUsedBreakdown: Map<String, Int>
)
