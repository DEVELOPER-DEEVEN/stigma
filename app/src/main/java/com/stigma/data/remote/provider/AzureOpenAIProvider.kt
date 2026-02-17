package com.stigma.data.remote.provider

import com.stigma.data.remote.AzureOpenAIApi

/**
 * Provider interface for Azure OpenAI API client.
 * 
 * This abstraction allows the underlying client to be swapped at runtime,
 * which is useful for testing, dependency injection, and durable execution
 * frameworks (like Temporal) where providers may be re-initialized.
 */
interface AzureOpenAIProvider {
    /**
     * Returns the current Azure OpenAI API client instance.
     * 
     * This should be implemented as a property that delegates to the
     * underlying client reference, not a cached value at construction time.
     */
    val client: AzureOpenAIApi
    
    /**
     * Returns the base URL for the Azure OpenAI endpoint.
     */
    val baseUrl: String
    
    /**
     * Returns the API version being used.
     */
    val apiVersion: String
}
