package com.stigma.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "claims")
data class ClaimEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val context: String,
    val createdAt: Long,
    val status: String, // PENDING, ANALYZING, COMPLETED, FAILED
    val updatedAt: Long
)
