package com.stigma.domain.repository

import com.stigma.domain.model.Claim
import kotlinx.coroutines.flow.Flow

interface ClaimRepository {
    fun getAllClaims(): Flow<List<Claim>>
    fun getClaimById(claimId: String): Flow<Claim?>
    fun getClaimsByStatus(status: String): Flow<List<Claim>>
    suspend fun insertClaim(claim: Claim)
    suspend fun updateClaim(claim: Claim)
    suspend fun deleteClaim(claimId: String)
    suspend fun syncWithRemote()
}
