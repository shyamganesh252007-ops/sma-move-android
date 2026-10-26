package com.smamove.android.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun resetState() {
        _uiState.value = AuthUiState()
    }

    fun submitPatientLogin(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(error = "Email and password are required") }
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1000) // Simulate network request
            val result = repository.loginPatient(email, pass)
            result.onSuccess {
                _uiState.update { state -> state.copy(isLoading = false, isSuccess = true) }
            }.onFailure { exception ->
                _uiState.update { state -> state.copy(isLoading = false, error = exception.message) }
            }
        }
    }

    fun submitInvitationCode(code: String) {
        if (code.isBlank()) {
            _uiState.update { it.copy(error = "Invitation code is required") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1000) // Simulate network request
            val result = repository.validateInvitationCode(code)
            result.onSuccess {
                _uiState.update { state -> state.copy(isLoading = false, isSuccess = true) }
            }.onFailure { exception ->
                _uiState.update { state -> state.copy(isLoading = false, error = exception.message) }
            }
        }
    }

    fun submitProfessionalDetails(email: String, pass: String, fullName: String, hprId: String) {
        if (email.isBlank() || pass.isBlank() || fullName.isBlank() || hprId.isBlank()) {
            _uiState.update { it.copy(error = "All fields are required for verification") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1500) // Simulate verification request
            val result = repository.verifyProfessional(email, hprId)
            result.onSuccess {
                _uiState.update { state -> state.copy(isLoading = false, isSuccess = true) }
            }.onFailure { exception ->
                _uiState.update { state -> state.copy(isLoading = false, error = exception.message) }
            }
        }
    }
}
