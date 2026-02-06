package com.stigma.data.repository

import com.google.gson.Gson
import com.stigma.data.local.dao.AnalysisDao
import com.stigma.data.local.mapper.toDomain
import com.stigma.data.local.mapper.toEntity
import com.stigma.domain.model.AnalysisResult
import com.stigma.domain.repository.AnalysisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalysisRepositoryImpl @Inject constructor(
    private val analysisDao: AnalysisDao,
    private val gson: Gson
) : AnalysisRepository {
    
    override fun getAllAnalyses(): Flow<List<AnalysisResult>> {
        return analysisDao.getAllAnalyses().map { entities ->
            entities.map { it.toDomain(gson) }
        }
    }
    
    override fun getAnalysisById(analysisId: String): Flow<AnalysisResult?> {
        return analysisDao.getAnalysisById(analysisId).map { it?.toDomain(gson) }
    }
    
    override fun getAnalysesByClaimId(claimId: String): Flow<List<AnalysisResult>> {
        return analysisDao.getAnalysesByClaimId(claimId).map { entities ->
            entities.map { it.toDomain(gson) }
        }
    }
    
    override fun getRecentAnalyses(limit: Int): Flow<List<AnalysisResult>> {
        return analysisDao.getRecentAnalyses(limit).map { entities ->
            entities.map { it.toDomain(gson) }
        }
    }
    
    override suspend fun insertAnalysis(analysis: AnalysisResult) {
        Timber.d("Inserting analysis for claim: ${analysis.claimId}")
        analysisDao.insertAnalysis(analysis.toEntity(gson))
    }
    
    override suspend fun deleteAnalysis(analysisId: String) {
        Timber.d("Deleting analysis: $analysisId")
        analysisDao.deleteAnalysisById(analysisId)
    }
    
    override suspend fun getAverageConfidence(): Float {
        return analysisDao.getAverageConfidenceScore() ?: 0f
    }
    
    override suspend fun getTotalCount(): Int {
        return analysisDao.getTotalAnalysesCount()
    }
    
    override suspend fun syncWithRemote() {
        // TODO: Implement Firestore sync
        Timber.d("Syncing analyses with remote")
    }
}
