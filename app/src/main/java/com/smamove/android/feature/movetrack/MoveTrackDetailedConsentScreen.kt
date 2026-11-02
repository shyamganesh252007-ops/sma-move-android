package com.smamove.android.feature.movetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
fun MoveTrackDetailedConsentScreen(
    onContinueToPreferences: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Informed Consent",
                onBack = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with clear category
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SmaStatusChip(
                    text = "INFORMED CONSENT · PROTOCOL REVIEW",
                    icon = Icons.Default.Shield,
                    containerColor = SagePrimaryFixed,
                    contentColor = OnSagePrimaryFixed
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Detailed Informed Consent",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Please review this document explaining how MoveTrack uses on-device camera vision for gentle posture tracking. Your autonomy and privacy are fully protected.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant,
                    lineHeight = 20.sp
                )
            }

            // Intro summary card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(OliveSecondaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = OnOliveSecondaryContainer
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Camera & Tracking Governance",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "MoveTrack operates under strict patient autonomy principles. Camera participation is 100% voluntary and never required to use other parts of SMA-Move.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnWarmStoneVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Section 1
            ConsentSectionCard(
                sectionNumber = "1",
                title = "Intended Purpose & Limitations",
                content = "MoveTrack is an optional, experimental movement guidance tool designed to assist individuals with SMA in observing posture and gentle range of motion. It is not a diagnostic medical device and does not replace qualified clinical evaluations.",
                bulletPoints = listOf(
                    "Provides non-diagnostic movement feedback for personal tracking.",
                    "Does not prescribe treatment or predict clinical outcomes.",
                    "Exercise caution and never exceed your comfort or clinician instructions.",
                    "No diagnostic accuracy or clinical claims are fabricated or implied."
                )
            )

            // Section 2
            ConsentSectionCard(
                sectionNumber = "2",
                title = "Data Processing & Edge Computing",
                content = "We prioritize privacy through strict edge computing. All pose landmark detection runs strictly on your local device processor in RAM.",
                bulletPoints = listOf(
                    "No video recordings or raw camera images are ever sent to cloud servers.",
                    "Only numerical joint coordinates are processed transiently in memory.",
                    "Video frames are discarded immediately after landmark extraction.",
                    "You may withdraw camera consent at any time from the Privacy Centre."
                )
            )

            // Section 3
            ConsentSectionCard(
                sectionNumber = "3",
                title = "Caregiver Sharing is Strictly Optional",
                content = "Sharing session updates with family or caregivers requires a separate, explicit opt-in preference. You remain in complete control over what is shared.",
                bulletPoints = listOf(
                    "Caregiver access is never enabled by default.",
                    "Preferences can be updated or revoked at any time.",
                    "No health records are transferred without authorized representative verification."
                )
            )

            // Action Buttons
            SmaPrimaryButton(
                text = "Proceed to Consent Choices",
                leadingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = onContinueToPreferences
            )

            SmaSecondaryButton(
                text = "Back to Overview",
                onClick = onBack
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ConsentSectionCard(
    sectionNumber: String,
    title: String,
    content: String,
    bulletPoints: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(SagePrimaryFixed, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = sectionNumber,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnSagePrimaryFixed
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
            }

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = OnWarmStoneVariant,
                lineHeight = 20.sp
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                bulletPoints.forEach { point ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = DeepOliveSecondary,
                            modifier = Modifier.size(18.dp).padding(top = 2.dp)
                        )
                        Text(
                            text = point,
                            style = MaterialTheme.typography.bodySmall,
                            color = OnWarmStoneVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

