package com.stigma.data.local.dao

import androidx.room.*
import com.stigma.data.local.entity.ClaimEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClaimDao {
    
    @Query("SELECT * FROM claims ORDER BY createdAt DESC")
    fun getAllClaims(): Flow<List<ClaimEntity>>
    
    @Query("SELECT * FROM claims WHERE id = :claimId")
    fun getClaimById(claimId: String): Flow<ClaimEntity?>
    
    @Query("SELECT * FROM claims WHERE status = :status ORDER BY createdAt DESC")
    fun getClaimsByStatus(status: String): Flow<List<ClaimEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaim(claim: ClaimEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaims(claims: List<ClaimEntity>)
    
    @Update
    suspend fun updateClaim(claim: ClaimEntity)
    
    @Delete
    suspend fun deleteClaim(claim: ClaimEntity)
    
    @Query("DELETE FROM claims WHERE id = :claimId")
    suspend fun deleteClaimById(claimId: String)
    
    @Query("DELETE FROM claims")
    suspend fun deleteAllClaims()
}
