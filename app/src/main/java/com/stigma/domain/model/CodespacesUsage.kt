package com.stigma.domain.model

/**
 * Domain model representing GitHub Codespaces allowance usage for the current billing cycle.
 *
 * @param totalMinutesUsed Total minutes used so far this month across all machine types.
 * @param totalPaidMinutesUsed Minutes used beyond the free-tier included allowance.
 * @param includedMinutes Total minutes included for free in the user's plan.
 * @param minutesUsedBreakdown Per-machine-type breakdown of minutes consumed.
 */
data class CodespacesUsage(
    val totalMinutesUsed: Int,
    val totalPaidMinutesUsed: Int,
    val includedMinutes: Int,
    val minutesUsedBreakdown: Map<String, Int>
)
