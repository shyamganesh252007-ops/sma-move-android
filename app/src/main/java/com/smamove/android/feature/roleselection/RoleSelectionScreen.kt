package com.smamove.android.feature.roleselection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smamove.android.ui.theme.SMAAndroidTheme

private val Teal = Color(0xFF087F72)
private val PageBackground = Color(0xFFF7F8FC)
private val CardBackground = Color(0xFFEDF2FF)
private val PrimaryText = Color(0xFF17243A)
private val SecondaryText = Color(0xFF586579)

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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PageBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PageBackground)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                color = Color(0xFFDCE8FF),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "SMA-MOVE  •  INDIA",
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),
                    color = Teal,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Text(
                text = "Welcome to SMA-Move",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )

            Text(
                text = "Your care, your movement, your choice.",
                style = MaterialTheme.typography.bodyLarge,
                color = SecondaryText
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
                    .height(54.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Teal
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
                    color = Color(0xFFDDF4EE),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(14.dp),
                        color = Color(0xFF075E54),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Privacy-first care · Sharing stays under your control",
                modifier = Modifier.fillMaxWidth(),
                color = SecondaryText,
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                Color(0xFFDDF1ED)
            } else {
                CardBackground
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
                        color = Teal,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = role.symbol,
                    color = Color.White,
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
                    color = PrimaryText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = role.description,
                    color = SecondaryText,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                if (role == UserRole.PROFESSIONAL) {
                    Text(
                        text = "Verification required",
                        color = Teal,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (selected) {
                Surface(
                    modifier = Modifier.size(25.dp),
                    shape = CircleShape,
                    color = Teal
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "✓",
                            color = Color.White,
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
