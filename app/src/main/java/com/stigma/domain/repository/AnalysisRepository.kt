package com.stigma.domain.repository

import com.stigma.domain.model.AnalysisResult
import kotlinx.coroutines.flow.Flow

interface AnalysisRepository {
    fun getAllAnalyses(): Flow<List<AnalysisResult>>
    fun getAnalysisById(analysisId: String): Flow<AnalysisResult?>
    fun getAnalysesByClaimId(claimId: String): Flow<List<AnalysisResult>>
    fun getRecentAnalyses(limit: Int): Flow<List<AnalysisResult>>
    suspend fun insertAnalysis(analysis: AnalysisResult)
    suspend fun deleteAnalysis(analysisId: String)
    suspend fun getAverageConfidence(): Float
    suspend fun getTotalCount(): Int
    suspend fun syncWithRemote()
}
