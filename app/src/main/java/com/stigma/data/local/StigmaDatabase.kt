package com.stigma.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stigma.data.local.dao.AnalysisDao
import com.stigma.data.local.dao.ClaimDao
import com.stigma.data.local.entity.AnalysisEntity
import com.stigma.data.local.entity.ClaimEntity

@Database(
    entities = [
        ClaimEntity::class,
        AnalysisEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class StigmaDatabase : RoomDatabase() {
    abstract fun claimDao(): ClaimDao
    abstract fun analysisDao(): AnalysisDao
}
