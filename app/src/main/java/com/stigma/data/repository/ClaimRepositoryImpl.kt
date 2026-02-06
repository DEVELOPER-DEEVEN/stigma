package com.stigma.data.repository

import com.google.gson.Gson
import com.stigma.data.local.dao.ClaimDao
import com.stigma.data.local.mapper.toDomain
import com.stigma.data.local.mapper.toEntity
import com.stigma.domain.model.Claim
import com.stigma.domain.repository.ClaimRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClaimRepositoryImpl @Inject constructor(
    private val claimDao: ClaimDao,
    private val gson: Gson
) : ClaimRepository {
    
    override fun getAllClaims(): Flow<List<Claim>> {
        return claimDao.getAllClaims().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override fun getClaimById(claimId: String): Flow<Claim?> {
        return claimDao.getClaimById(claimId).map { it?.toDomain() }
    }
    
    override fun getClaimsByStatus(status: String): Flow<List<Claim>> {
        return claimDao.getClaimsByStatus(status).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun insertClaim(claim: Claim) {
        Timber.d("Inserting claim: ${claim.title}")
        claimDao.insertClaim(claim.toEntity())
    }
    
    override suspend fun updateClaim(claim: Claim) {
        Timber.d("Updating claim: ${claim.id}")
        claimDao.updateClaim(claim.toEntity())
    }
    
    override suspend fun deleteClaim(claimId: String) {
        Timber.d("Deleting claim: $claimId")
        claimDao.deleteClaimById(claimId)
    }
    
    override suspend fun syncWithRemote() {
        // TODO: Implement Firestore sync
        Timber.d("Syncing claims with remote")
    }
}
