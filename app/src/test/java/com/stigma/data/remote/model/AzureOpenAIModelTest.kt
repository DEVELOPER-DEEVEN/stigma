package com.stigma.data.remote.model

import com.stigma.data.remote.AzureOpenAIApi
import com.stigma.data.remote.dto.AzureOpenAIRequest
import com.stigma.data.remote.dto.AzureOpenAIResponse
import com.stigma.data.remote.dto.Choice
import com.stigma.data.remote.dto.Message
import com.stigma.data.remote.dto.Usage
import com.stigma.data.remote.provider.AzureOpenAIProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Test that AzureOpenAIModel uses a property for `client` to avoid stale references.
 * 
 * When a provider's client is replaced (e.g., in durable execution environments
 * like Temporal, or in testing with dependency injection), the model should reflect
 * the updated client, not hold a stale reference from construction time.
 * 
 * This test is inspired by: https://github.com/pydantic/pydantic-ai/issues/4336
 */
class AzureOpenAIModelTest {
    
    private lateinit var mockClient: AzureOpenAIApi
    private lateinit var mockProvider: AzureOpenAIProvider
    private val testApiKey = "test-api-key"
    private val testDeploymentId = "test-deployment"
    
    @Before
    fun setup() {
        mockClient = mock()
        mockProvider = mock()
    }
    
    @Test
    fun `client property reflects provider client changes`() {
        // Create a mock provider that can have its client swapped
        val client1 = mock<AzureOpenAIApi>()
        val client2 = mock<AzureOpenAIApi>()
        
        // Create a test provider implementation
        val provider = object : AzureOpenAIProvider {
            private var _client: AzureOpenAIApi = client1
            
            override val client: AzureOpenAIApi
                get() = _client
            
            override val baseUrl: String = "https://test.openai.azure.com/"
            
            override val apiVersion: String = "2024-02-01"
            
            fun updateClient(newClient: AzureOpenAIApi) {
                _client = newClient
            }
        }
        
        // Create the model with the provider
        val model = AzureOpenAIModel(
            provider = provider,
            apiKey = testApiKey,
            deploymentId = testDeploymentId
        )
        
        // Initially, model should use client1
        assertSame("Model should initially use client1", client1, model.client)
        
        // Swap the provider's client
        provider.updateClient(client2)
        
        // Model should now reflect the new client, not the stale one
        assertSame("Model should now use client2 after provider update", client2, model.client)
        assertNotSame("Model should not use the old client1", client1, model.client)
    }
    
    @Test
    fun `client is not cached at construction time`() {
        // Create a provider that returns different clients each time
        var clientCounter = 0
        val provider = object : AzureOpenAIProvider {
            override val client: AzureOpenAIApi
                get() = mock<AzureOpenAIApi>().also { clientCounter++ }
            
            override val baseUrl: String = "https://test.openai.azure.com/"
            override val apiVersion: String = "2024-02-01"
        }
        
        val model = AzureOpenAIModel(
            provider = provider,
            apiKey = testApiKey,
            deploymentId = testDeploymentId
        )
        
        // Access client multiple times
        val client1 = model.client
        val client2 = model.client
        val client3 = model.client
        
        // Each access should call the provider's client property
        assertEquals("Client property should be called 3 times", 3, clientCounter)
    }
    
    @Test
    fun `analyzeText uses current client from provider`() = runTest {
        val mockResponse = AzureOpenAIResponse(
            id = "test-id",
            objectType = "chat.completion",
            created = System.currentTimeMillis(),
            model = "gpt-4",
            choices = listOf(
                Choice(
                    index = 0,
                    message = Message(role = "assistant", content = "Test response"),
                    finishReason = "stop"
                )
            ),
            usage = Usage(
                promptTokens = 10,
                completionTokens = 5,
                totalTokens = 15
            )
        )
        
        whenever(mockClient.analyzeText(any(), any(), any())).thenReturn(mockResponse)
        whenever(mockProvider.client).thenReturn(mockClient)
        whenever(mockProvider.baseUrl).thenReturn("https://test.openai.azure.com/")
        whenever(mockProvider.apiVersion).thenReturn("2024-02-01")
        
        val model = AzureOpenAIModel(
            provider = mockProvider,
            apiKey = testApiKey,
            deploymentId = testDeploymentId
        )
        
        val request = AzureOpenAIRequest(
            messages = listOf(Message(role = "user", content = "Test message"))
        )
        
        val response = model.analyzeText(request)
        
        assertEquals("Response should match mock response", mockResponse, response)
    }
    
    @Test
    fun `baseUrl delegates to provider`() {
        val expectedUrl = "https://custom.openai.azure.com/"
        whenever(mockProvider.baseUrl).thenReturn(expectedUrl)
        whenever(mockProvider.client).thenReturn(mockClient)
        whenever(mockProvider.apiVersion).thenReturn("2024-02-01")
        
        val model = AzureOpenAIModel(
            provider = mockProvider,
            apiKey = testApiKey,
            deploymentId = testDeploymentId
        )
        
        assertEquals("Base URL should come from provider", expectedUrl, model.baseUrl)
    }
    
    @Test
    fun `apiVersion delegates to provider`() {
        val expectedVersion = "2024-03-01"
        whenever(mockProvider.apiVersion).thenReturn(expectedVersion)
        whenever(mockProvider.client).thenReturn(mockClient)
        whenever(mockProvider.baseUrl).thenReturn("https://test.openai.azure.com/")
        
        val model = AzureOpenAIModel(
            provider = mockProvider,
            apiKey = testApiKey,
            deploymentId = testDeploymentId
        )
        
        assertEquals("API version should come from provider", expectedVersion, model.apiVersion)
    }
}
