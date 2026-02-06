package com.stigma.data.local.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stigma.data.local.entity.AnalysisEntity
import com.stigma.data.local.entity.ClaimEntity
import com.stigma.domain.model.*

/**
 * Mappers between domain models and database entities
 */

// Claim mappers
fun Claim.toEntity(): ClaimEntity {
    return ClaimEntity(
        id = id,
        title = title,
        description = description,
        context = context,
        createdAt = createdAt,
        status = status.name,
        updatedAt = System.currentTimeMillis()
    )
}

fun ClaimEntity.toDomain(): Claim {
    return Claim(
        id = id,
        title = title,
        description = description,
        context = context,
        createdAt = createdAt,
        status = AnalysisStatus.valueOf(status)
    )
}

// Analysis mappers
fun AnalysisResult.toEntity(gson: Gson): AnalysisEntity {
    return AnalysisEntity(
        id = claimId + "_" + completedAt,
        claimId = claimId,
        executiveSummary = executiveSummary,
        stanceFraming = stanceFraming,
        strategicRecommendation = strategicRecommendation,
        confidenceScore = confidenceScore,
        parametersJson = gson.toJson(parameters),
        patternsJson = gson.toJson(patterns),
        supportingTrendsJson = gson.toJson(supportingTrends),
        correlationAnalysis = correlationAnalysis,
        comparativeMetricsJson = gson.toJson(comparativeMetrics),
        scenarioAdvantagesJson = gson.toJson(scenarioAdvantages),
        limitationsJson = gson.toJson(limitations),
        dataSourcesJson = gson.toJson(dataSources),
        completedAt = completedAt,
        createdAt = System.currentTimeMillis()
    )
}

fun AnalysisEntity.toDomain(gson: Gson): AnalysisResult {
    val parametersType = object : TypeToken<List<AnalysisParameter>>() {}.type
    val patternsType = object : TypeToken<List<DiscoveredPattern>>() {}.type
    val trendsType = object : TypeToken<List<String>>() {}.type
    val metricsType = object : TypeToken<List<ComparisonMetric>>() {}.type
    val scenariosType = object : TypeToken<List<Scenario>>() {}.type
    val stringsType = object : TypeToken<List<String>>() {}.type
    
    return AnalysisResult(
        claimId = claimId,
        normalizedClaim = NormalizedClaim(
            decisionObjective = executiveSummary,
            evaluationCriteria = emptyList(),
            contextAssumptions = emptyList(),
            timeframe = "",
            scope = ""
        ),
        parameters = gson.fromJson(parametersJson, parametersType),
        patterns = gson.fromJson(patternsJson, patternsType),
        executiveSummary = executiveSummary,
        stanceFraming = stanceFraming,
        supportingTrends = gson.fromJson(supportingTrendsJson, trendsType),
        correlationAnalysis = correlationAnalysis,
        comparativeMetrics = gson.fromJson(comparativeMetricsJson, metricsType),
        scenarioAdvantages = gson.fromJson(scenarioAdvantagesJson, scenariosType),
        strategicRecommendation = strategicRecommendation,
        confidenceScore = confidenceScore,
        limitations = gson.fromJson(limitationsJson, stringsType),
        dataSources = gson.fromJson(dataSourcesJson, stringsType),
        completedAt = completedAt
    )
}
