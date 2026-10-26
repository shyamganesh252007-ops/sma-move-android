package com.smamove.android.feature.roleselection

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class UserRole(
    val title: String,
    val description: String,
    val symbol: String
) {
    PATIENT(
        title = "Patient",
        description = "Your movement assessments, wellbeing and personal care.",
        symbol = "✳"
    ),
    FAMILY(
        title = "Family / Caregiver",
        description = "Coordinate care through patient-approved access.",
        symbol = "♧"
    ),
    PROFESSIONAL(
        title = "Healthcare Professional",
        description = "Professional sign-in and credential verification.",
        symbol = "+"
    )
}

data class RoleSelectionState(
    val selectedRole: UserRole = UserRole.PATIENT,
    val message: String? = null
)

class RoleSelectionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RoleSelectionState())
    val uiState: StateFlow<RoleSelectionState> = _uiState.asStateFlow()

    fun selectRole(role: UserRole) {
        _uiState.update { it.copy(selectedRole = role, message = null) }
    }

    fun onContinueClicked(onNavigate: (UserRole) -> Unit) {
        val currentRole = _uiState.value.selectedRole
        onNavigate(currentRole)
    }
}
