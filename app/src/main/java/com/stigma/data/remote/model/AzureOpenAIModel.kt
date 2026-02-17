package com.stigma.data.remote.model

import com.stigma.data.remote.AzureOpenAIApi
import com.stigma.data.remote.dto.AzureOpenAIRequest
import com.stigma.data.remote.dto.AzureOpenAIResponse
import com.stigma.data.remote.provider.AzureOpenAIProvider
import javax.inject.Inject

/**
 * Azure OpenAI Model wrapper that properly delegates to the provider's client.
 * 
 * This class follows the pattern from pydantic-ai PR #4276 to avoid stale client references.
 * Instead of caching the client at construction time, it uses a property that always
 * delegates to the provider's current client instance.
 * 
 * This is important in scenarios where:
 * - Providers may be re-initialized (durable execution frameworks like Temporal)
 * - Testing with dependency injection where clients are swapped
 * - Dynamic client configuration changes
 * 
 * @param provider The Azure OpenAI provider that supplies the client instance
 * @param apiKey The API key for authentication
 * @param deploymentId The deployment ID for the model
 */
class AzureOpenAIModel @Inject constructor(
    private val provider: AzureOpenAIProvider,
    private val apiKey: String,
    private val deploymentId: String
) {
    
    /**
     * Returns the current client from the provider.
     * 
     * This is implemented as a property that delegates to provider.client,
     * NOT as a field cached at construction time. This ensures that if the
     * provider's client is swapped, this model will use the new client.
     */
    val client: AzureOpenAIApi
        get() = provider.client
    
    /**
     * Returns the base URL from the provider.
     */
    val baseUrl: String
        get() = provider.baseUrl
    
    /**
     * Returns the API version from the provider.
     */
    val apiVersion: String
        get() = provider.apiVersion
    
    /**
     * Analyzes text using the Azure OpenAI API.
     * 
     * @param request The request containing messages and parameters
     * @return The response from the Azure OpenAI API
     */
    suspend fun analyzeText(request: AzureOpenAIRequest): AzureOpenAIResponse {
        return client.analyzeText(
            apiKey = apiKey,
            apiVersion = apiVersion,
            request = request
        )
    }
}
