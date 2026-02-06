package com.stigma.di

import android.content.Context
import androidx.room.Room
import com.stigma.data.local.StigmaDatabase
import com.stigma.data.local.dao.AnalysisDao
import com.stigma.data.local.dao.ClaimDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideStigmaDatabase(
        @ApplicationContext context: Context
    ): StigmaDatabase {
        return Room.databaseBuilder(
            context,
            StigmaDatabase::class.java,
            "stigma_database"
        )
            .fallbackToDestructiveMigration() // TODO: Add proper migrations for production
            .build()
    }
    
    @Provides
    fun provideClaimDao(database: StigmaDatabase): ClaimDao {
        return database.claimDao()
    }
    
    @Provides
    fun provideAnalysisDao(database: StigmaDatabase): AnalysisDao {
        return database.analysisDao()
    }
}
