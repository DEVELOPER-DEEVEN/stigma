package com.stigma.domain.model

data class AnalysisResult(
    val id: String,
    val claimId: String,
    val status: AnalysisStatus,
    val normalizedClaim: NormalizedClaim,
    val parameters: List<AnalysisParameter>,
    val patterns: List<DiscoveredPattern>,
    val metrics: List<ComparisonMetric>,
    val scenarios: List<Scenario>,
    val createdAt: Long,
    val updatedAt: Long
)

enum class AnalysisStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
}

data class NormalizedClaim(
    val text: String,
    val confidence: Double
)

data class AnalysisParameter(
    val name: String,
    val value: String,
    val confidence: Double
)

data class DiscoveredPattern(
    val type: String,
    val description: String,
    val significance: Double
)

data class ComparisonMetric(
    val metric: String,
    val value: Double,
    val baseline: Double
)

data class Scenario(
    val id: String,
    val description: String,
    val probability: Double
)
