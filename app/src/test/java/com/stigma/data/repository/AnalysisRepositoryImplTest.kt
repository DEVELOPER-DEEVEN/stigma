package com.stigma.data.repository

import com.google.gson.Gson
import com.stigma.data.local.dao.AnalysisDao
import com.stigma.domain.model.AnalysisResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AnalysisRepositoryImplTest {

    private lateinit var analysisDao: AnalysisDao
    private lateinit var gson: Gson
    private lateinit var repository: AnalysisRepositoryImpl

    @Before
    fun setUp() {
        analysisDao = mockk()
        gson = Gson()
        repository = AnalysisRepositoryImpl(analysisDao, gson)
    }

    @Test
    fun `getAverageConfidence returns 0 when dao returns null`() = runTest {
        coEvery { analysisDao.getAverageConfidenceScore() } returns null
        
        val result = repository.getAverageConfidence()
        
        assertEquals(0f, result)
    }

    @Test
    fun `getTotalCount returns correct count from dao`() = runTest {
        coEvery { analysisDao.getTotalAnalysesCount() } returns 42
        
        val result = repository.getTotalCount()
        
        assertEquals(42, result)
    }
}
