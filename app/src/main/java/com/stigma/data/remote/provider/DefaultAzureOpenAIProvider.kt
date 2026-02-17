package com.stigma.data.remote.provider

import com.stigma.data.remote.AzureOpenAIApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Default implementation of [AzureOpenAIProvider].
 * 
 * This implementation holds a reference to the Azure OpenAI API client
 * and delegates all property access to it. The client reference can be
 * updated at runtime if needed (e.g., for testing or provider swap scenarios).
 */
@Singleton
class DefaultAzureOpenAIProvider @Inject constructor(
    private val azureOpenAIApi: AzureOpenAIApi,
    private val endpoint: String,
    private val version: String
) : AzureOpenAIProvider {
    
    /**
     * Internal mutable reference to the client.
     * This allows for swapping the client at runtime in testing scenarios.
     */
    @Volatile
    private var _client: AzureOpenAIApi = azureOpenAIApi
    
    override val client: AzureOpenAIApi
        get() = _client
    
    override val baseUrl: String
        get() = endpoint
    
    override val apiVersion: String
        get() = version
    
    /**
     * Updates the client instance.
     * This is primarily for testing purposes and should not be used in production code.
     * 
     * @param newClient The new client instance to use.
     */
    internal fun updateClient(newClient: AzureOpenAIApi) {
        _client = newClient
    }
}
