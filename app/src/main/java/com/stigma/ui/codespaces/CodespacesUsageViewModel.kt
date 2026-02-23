package com.stigma.ui.codespaces

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stigma.domain.model.CodespacesUsage
import com.stigma.domain.repository.CodespacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state for the Codespaces usage screen. */
sealed class CodespacesUsageUiState {
    data object Loading : CodespacesUsageUiState()
    data class Success(val usage: CodespacesUsage) : CodespacesUsageUiState()
    data class Error(val message: String) : CodespacesUsageUiState()
    data object TokenRequired : CodespacesUsageUiState()
}

@HiltViewModel
class CodespacesUsageViewModel @Inject constructor(
    private val repository: CodespacesRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow<CodespacesUsageUiState>(CodespacesUsageUiState.Loading)
    val uiState: StateFlow<CodespacesUsageUiState> = _uiState.asStateFlow()

    init {
        loadUsage()
    }

    fun loadUsage() {
        viewModelScope.launch {
            _uiState.value = CodespacesUsageUiState.Loading
            val token = dataStore.data.map { prefs ->
                prefs[GITHUB_TOKEN_KEY].orEmpty()
            }.first()
            if (token.isBlank()) {
                _uiState.value = CodespacesUsageUiState.TokenRequired
                return@launch
            }
            repository.getUsage(token)
                .onSuccess { usage -> _uiState.value = CodespacesUsageUiState.Success(usage) }
                .onFailure { e ->
                    _uiState.value = CodespacesUsageUiState.Error(
                        e.message ?: "Failed to fetch Codespaces usage."
                    )
                }
        }
    }

    /** Persists the GitHub PAT and immediately fetches usage. */
    fun saveTokenAndLoad(token: String) {
        viewModelScope.launch {
            dataStore.edit { prefs -> prefs[GITHUB_TOKEN_KEY] = token }
            loadUsage()
        }
    }

    companion object {
        private val GITHUB_TOKEN_KEY = stringPreferencesKey("github_token")
    }
}

