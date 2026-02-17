# Client Property Pattern - Avoiding Stale References

## Overview

This document explains the client property pattern implemented in STIGMA to avoid stale client references, inspired by [pydantic-ai PR #4276](https://github.com/pydantic/pydantic-ai/pull/4276).

## The Problem

When a model class stores a client instance at construction time (e.g., `val client = provider.client`), it caches a reference to that specific client object. This becomes problematic in scenarios where:

1. **Durable Execution Frameworks** (e.g., Temporal): Providers may be re-initialized after workflow suspension/resumption
2. **Testing**: Dependency injection frameworks may swap clients for mocking
3. **Dynamic Configuration**: Runtime changes to API endpoints or credentials

### Anti-Pattern (DO NOT USE)

```kotlin
class BadModel @Inject constructor(
    private val provider: AzureOpenAIProvider
) {
    // ❌ BAD: Caches client at construction time
    private val client = provider.client
    
    suspend fun analyze(request: AzureOpenAIRequest): AzureOpenAIResponse {
        return client.analyzeText(...) // Uses stale client!
    }
}
```

## The Solution

Use a **property with a getter** that delegates to the provider's current client instance:

### Correct Pattern (USE THIS)

```kotlin
class AzureOpenAIModel @Inject constructor(
    private val provider: AzureOpenAIProvider
) {
    // ✅ GOOD: Property delegates to provider's current client
    val client: AzureOpenAIApi
        get() = provider.client
    
    suspend fun analyzeText(request: AzureOpenAIRequest): AzureOpenAIResponse {
        return client.analyzeText(...) // Always uses current client!
    }
}
```

## Implementation in STIGMA

### 1. Provider Interface

`AzureOpenAIProvider.kt` defines the abstraction:

```kotlin
interface AzureOpenAIProvider {
    val client: AzureOpenAIApi  // Property, not a field
    val baseUrl: String
    val apiVersion: String
}
```

### 2. Provider Implementation

`DefaultAzureOpenAIProvider.kt` implements the provider with swappable client:

```kotlin
class DefaultAzureOpenAIProvider @Inject constructor(
    private val azureOpenAIApi: AzureOpenAIApi,
    private val endpoint: String,
    private val version: String
) : AzureOpenAIProvider {
    
    @Volatile
    private var _client: AzureOpenAIApi = azureOpenAIApi
    
    override val client: AzureOpenAIApi
        get() = _client  // Always returns current client
        
    // For testing: allows client swap
    internal fun updateClient(newClient: AzureOpenAIApi) {
        _client = newClient
    }
}
```

### 3. Model Class

`AzureOpenAIModel.kt` uses the provider correctly:

```kotlin
class AzureOpenAIModel @Inject constructor(
    private val provider: AzureOpenAIProvider,
    private val apiKey: String,
    private val deploymentId: String
) {
    // Property delegates to provider
    val client: AzureOpenAIApi
        get() = provider.client
        
    val baseUrl: String
        get() = provider.baseUrl
        
    val apiVersion: String
        get() = provider.apiVersion
}
```

## Testing

The pattern is verified with unit tests in `AzureOpenAIModelTest.kt`:

```kotlin
@Test
fun `client property reflects provider client changes`() {
    val client1 = mock<AzureOpenAIApi>()
    val client2 = mock<AzureOpenAIApi>()
    
    val provider = /* testable provider */
    val model = AzureOpenAIModel(provider, apiKey, deploymentId)
    
    assertSame(client1, model.client)  // Initially uses client1
    
    provider.updateClient(client2)      // Swap client
    
    assertSame(client2, model.client)  // Now uses client2
    assertNotSame(client1, model.client) // No longer uses stale client1
}
```

## Key Principles

1. **Never cache the client**: Don't assign `provider.client` to a field in `__init__`/constructor
2. **Use properties with getters**: Always delegate to the provider's current client
3. **Make providers swappable**: Allow client updates for testing and dynamic scenarios
4. **Document the pattern**: Help future developers understand why this approach is used

## Benefits

- ✅ Works correctly with durable execution frameworks
- ✅ Supports dependency injection and mocking in tests
- ✅ Enables runtime configuration changes
- ✅ Prevents hard-to-debug stale reference bugs

## References

- [pydantic-ai PR #4276](https://github.com/pydantic/pydantic-ai/pull/4276): Original Python implementation
- [pydantic-ai Issue #4336](https://github.com/pydantic/pydantic-ai/issues/4336): Issue description
