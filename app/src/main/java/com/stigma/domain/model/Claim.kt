package com.stigma.domain.model

data class Claim(
    val id: String,
    val text: String,
    val timestamp: Long,
    val source: String? = null
)
