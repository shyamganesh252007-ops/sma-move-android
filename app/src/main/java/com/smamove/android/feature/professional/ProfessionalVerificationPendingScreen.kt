package com.smamove.android.feature.professional

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalVerificationPendingScreen(
    onExploreWorkspace: () -> Unit,
    onBackToLogin: () -> Unit
) {
    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Verification Status",
                onBack = onBackToLogin
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Status Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = TerracottaTertiaryFixed)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SmaStatusChip(
                        text = "AUDIT IN PROGRESS",
                        icon = Icons.Default.HourglassTop,
                        containerColor = TerracottaTertiary,
                        contentColor = OnTerracottaTertiary
                    )

                    Text(
                        text = "Verification Under Review",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = OnTerracottaTertiaryFixed
                    )

                    Text(
                        text = "Your submission package (#HPR-IND-2026-9041) is currently queued for clinical credentialing against the Indian Healthcare Professionals Registry.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnTerracottaTertiaryFixedVariant,
                        lineHeight = 20.sp
                    )

                    Surface(
                        color = WarmStoneBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Estimated verification window: 2 to 3 business days. You will receive an SMS and email confirmation once verified.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OutlineColor,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            // Timeline Steps
            Text(
                text = "Verification Audit Timeline",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    AuditStepRow(
                        stepNumber = "1",
                        title = "Documentation Received",
                        desc = "Council certificate and staff ID verified on-device.",
                        isDone = true
                    )
                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))
                    AuditStepRow(
                        stepNumber = "2",
                        title = "State Medical Board & HPR Cross-Check",
                        desc = "Comparing registration against DMC active practitioner register.",
                        isInProgress = true
                    )
                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))
                    AuditStepRow(
                        stepNumber = "3",
                        title = "Institutional Telehealth Authority",
                        desc = "Enabling patient assignment and remote care plan issuance.",
                        isPending = true
                    )
                }
            }

            // Honest Prototype Notice
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = SurfaceContainerHigh
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = SagePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Demo Mode Access: While live HPR sync is pending in this evaluation build, you can inspect the authorized clinician workspace below to evaluate clinical workflows.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            // Action
            SmaPrimaryButton(
                text = "Preview Clinician Workspace (Demo)",
                leadingIcon = Icons.Default.MedicalServices,
                onClick = onExploreWorkspace
            )

            SmaSecondaryButton(
                text = "Return to Sign In",
                onClick = onBackToLogin
            )
        }
    }
}

@Composable
private fun AuditStepRow(
    stepNumber: String,
    title: String,
    desc: String,
    isDone: Boolean = false,
    isInProgress: Boolean = false,
    isPending: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    when {
                        isDone -> OliveSecondaryContainer
                        isInProgress -> TerracottaTertiaryContainer
                        else -> SurfaceContainerHigh
                    },
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = OnOliveSecondaryContainer,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Text(
                    text = stepNumber,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isInProgress) OnTerracottaTertiaryContainer else OutlineColor
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = OnWarmStoneVariant
            )
        }
    }
}

