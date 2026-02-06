package com.stigma.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analyses")
data class AnalysisEntity(
    @PrimaryKey
    val id: String,
    val claimId: String,
    val executiveSummary: String,
    val stanceFraming: String,
    val strategicRecommendation: String,
    val confidenceScore: Float,
    val parametersJson: String, // Serialized list of parameters
    val patternsJson: String, // Serialized list of patterns
    val supportingTrendsJson: String, // Serialized list
    val correlationAnalysis: String,
    val comparativeMetricsJson: String, // Serialized list
    val scenarioAdvantagesJson: String, // Serialized list
    val limitationsJson: String, // Serialized list
    val dataSourcesJson: String, // Serialized list
    val completedAt: Long,
    val createdAt: Long
)
