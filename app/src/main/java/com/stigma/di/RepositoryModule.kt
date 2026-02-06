package com.stigma.di

import com.stigma.data.repository.AnalysisRepositoryImpl
import com.stigma.data.repository.ClaimRepositoryImpl
import com.stigma.domain.repository.AnalysisRepository
import com.stigma.domain.repository.ClaimRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindClaimRepository(
        claimRepositoryImpl: ClaimRepositoryImpl
    ): ClaimRepository
    
    @Binds
    @Singleton
    abstract fun bindAnalysisRepository(
        analysisRepositoryImpl: AnalysisRepositoryImpl
    ): AnalysisRepository
}
