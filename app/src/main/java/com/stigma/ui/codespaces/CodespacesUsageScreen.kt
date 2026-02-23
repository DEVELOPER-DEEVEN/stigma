package com.stigma.ui.codespaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stigma.domain.model.CodespacesUsage
import com.stigma.ui.components.premium.GlassCard
import com.stigma.ui.components.premium.GlassCardElevated
import com.stigma.ui.theme.*

/**
 * Screen that displays the authenticated user's GitHub Codespaces allowance usage
 * for the current billing month.
 */
@Composable
fun CodespacesUsageScreen(
    viewModel: CodespacesUsageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(StigmaSurface, StigmaSurfaceElevated)
                )
            )
    ) {
        when (val state = uiState) {
            is CodespacesUsageUiState.Loading -> LoadingContent()
            is CodespacesUsageUiState.Success -> UsageContent(
                usage = state.usage,
                onRefresh = viewModel::loadUsage
            )
            is CodespacesUsageUiState.Error -> ErrorContent(
                message = state.message,
                onRetry = viewModel::loadUsage
            )
            is CodespacesUsageUiState.TokenRequired -> TokenInputContent(
                onTokenSubmit = viewModel::saveTokenAndLoad
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = StigmaPrimary)
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineSmall,
            color = StigmaError,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = StigmaTextSecondary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = StigmaPrimary)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Retry")
        }
    }
}

@Composable
private fun UsageContent(usage: CodespacesUsage, onRefresh: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Codespaces Usage",
                    style = MaterialTheme.typography.headlineMedium,
                    color = StigmaTextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Current billing month",
                    style = MaterialTheme.typography.bodySmall,
                    color = StigmaTextSecondary
                )
            }
            IconButton(onClick = onRefresh) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = StigmaNeonPurple
                )
            }
        }

        // Summary card
        GlassCardElevated(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                UsageStatRow(
                    label = "Minutes Used",
                    value = "${usage.totalMinutesUsed}",
                    valueColor = StigmaNeonBlue
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = StigmaGlassStroke
                )
                UsageStatRow(
                    label = "Included Minutes",
                    value = "${usage.includedMinutes}",
                    valueColor = StigmaSuccess
                )
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = StigmaGlassStroke
                )
                UsageStatRow(
                    label = "Paid Minutes",
                    value = "${usage.totalPaidMinutesUsed}",
                    valueColor = if (usage.totalPaidMinutesUsed > 0) StigmaWarning else StigmaTextSecondary
                )
            }
        }

        // Usage progress bar
        if (usage.includedMinutes > 0) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    val progress = (usage.totalMinutesUsed.toFloat() / usage.includedMinutes)
                        .coerceIn(0f, 1f)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Allowance",
                            style = MaterialTheme.typography.labelLarge,
                            color = StigmaTextPrimary
                        )
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            style = MaterialTheme.typography.labelLarge,
                            color = StigmaNeonCyan,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = when {
                            progress >= 0.9f -> StigmaError
                            progress >= 0.7f -> StigmaWarning
                            else -> StigmaSuccess
                        },
                        trackColor = StigmaGlassStroke
                    )
                }
            }
        }

        // Per-machine breakdown
        if (usage.minutesUsedBreakdown.isNotEmpty()) {
            Text(
                text = "Breakdown by Machine Type",
                style = MaterialTheme.typography.titleMedium,
                color = StigmaTextPrimary,
                fontWeight = FontWeight.SemiBold
            )
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    usage.minutesUsedBreakdown.entries.forEachIndexed { index, (machineType, minutes) ->
                        if (index > 0) {
                            Divider(
                                modifier = Modifier.padding(vertical = 10.dp),
                                color = StigmaGlassStroke
                            )
                        }
                        UsageStatRow(
                            label = machineType,
                            value = "$minutes min",
                            valueColor = StigmaNeonBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UsageStatRow(
    label: String,
    value: String,
    valueColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = StigmaTextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = valueColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun TokenInputContent(onTokenSubmit: (String) -> Unit) {
    var token by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "GitHub Token Required",
            style = MaterialTheme.typography.headlineSmall,
            color = StigmaTextPrimary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter a Personal Access Token with the 'codespace' scope to view your Codespaces usage.",
            style = MaterialTheme.typography.bodyMedium,
            color = StigmaTextSecondary
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = token,
            onValueChange = { token = it },
            label = { Text("GitHub Personal Access Token") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (token.isNotBlank()) onTokenSubmit(token) }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = StigmaPrimary,
                unfocusedBorderColor = StigmaGlassStroke,
                focusedLabelColor = StigmaPrimary,
                cursorColor = StigmaPrimary
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { if (token.isNotBlank()) onTokenSubmit(token) },
            enabled = token.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = StigmaPrimary)
        ) {
            Text("View Codespaces Usage")
        }
    }
}
