package com.stigma.data.local.dao

import androidx.room.*
import com.stigma.data.local.entity.AnalysisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {
    
    @Query("SELECT * FROM analyses ORDER BY completedAt DESC")
    fun getAllAnalyses(): Flow<List<AnalysisEntity>>
    
    @Query("SELECT * FROM analyses WHERE id = :analysisId")
    fun getAnalysisById(analysisId: String): Flow<AnalysisEntity?>
    
    @Query("SELECT * FROM analyses WHERE claimId = :claimId")
    fun getAnalysesByClaimId(claimId: String): Flow<List<AnalysisEntity>>
    
    @Query("SELECT * FROM analyses ORDER BY completedAt DESC LIMIT :limit")
    fun getRecentAnalyses(limit: Int): Flow<List<AnalysisEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AnalysisEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalyses(analyses: List<AnalysisEntity>)
    
    @Update
    suspend fun updateAnalysis(analysis: AnalysisEntity)
    
    @Delete
    suspend fun deleteAnalysis(analysis: AnalysisEntity)
    
    @Query("DELETE FROM analyses WHERE id = :analysisId")
    suspend fun deleteAnalysisById(analysisId: String)
    
    @Query("DELETE FROM analyses WHERE claimId = :claimId")
    suspend fun deleteAnalysesByClaimId(claimId: String)
    
    @Query("DELETE FROM analyses")
    suspend fun deleteAllAnalyses()
    
    @Query("SELECT AVG(confidenceScore) FROM analyses")
    suspend fun getAverageConfidenceScore(): Float?
    
    @Query("SELECT COUNT(*) FROM analyses")
    suspend fun getTotalAnalysesCount(): Int
}
