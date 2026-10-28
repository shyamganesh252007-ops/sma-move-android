package com.smamove.android.feature.roleselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smamove.android.ui.theme.SMAAndroidTheme

@Composable
fun RoleSelectionScreen(
    onNavigateToRole: (UserRole) -> Unit,
    viewModel: RoleSelectionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RoleSelectionContent(
        uiState = uiState,
        onRoleSelected = viewModel::selectRole,
        onContinueClicked = { viewModel.onContinueClicked(onNavigateToRole) }
    )
}

@Composable
private fun RoleSelectionContent(
    uiState: RoleSelectionState,
    onRoleSelected: (UserRole) -> Unit,
    onContinueClicked: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colors.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                color = colors.secondaryContainer,
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "SMA-MOVE  •  INDIA",
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),
                    color = colors.onSecondaryContainer,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Text(
                text = "Welcome to SMA-Move",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )

            Text(
                text = "Your care, your movement, your choice.",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            UserRole.entries.forEach { role ->
                RoleCard(
                    role = role,
                    selected = uiState.selectedRole == role,
                    onClick = { onRoleSelected(role) }
                )
            }

            Button(
                onClick = onContinueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "→", fontSize = 20.sp)
            }

            uiState.message?.let {
                Surface(
                    color = colors.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(14.dp),
                        color = colors.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Privacy-first care · Sharing stays under your control",
                modifier = Modifier.fillMaxWidth(),
                color = colors.onSurfaceVariant,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun RoleCard(
    role: UserRole,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                colors.primaryContainer
            } else {
                colors.surfaceVariant
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 3.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = colors.primary,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = role.symbol,
                    color = colors.onPrimary,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp, end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = role.title,
                    color = colors.onSurface,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = role.description,
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                if (role == UserRole.PROFESSIONAL) {
                    Text(
                        text = "Verification required",
                        color = colors.primary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (selected) {
                Surface(
                    modifier = Modifier.size(25.dp),
                    shape = CircleShape,
                    color = colors.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "✓",
                            color = colors.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoleSelectionScreenPreview() {
    SMAAndroidTheme {
        RoleSelectionContent(
            uiState = RoleSelectionState(),
            onRoleSelected = {},
            onContinueClicked = {}
        )
    }
}
