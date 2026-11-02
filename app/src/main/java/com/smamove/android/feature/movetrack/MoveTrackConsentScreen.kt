package com.smamove.android.feature.movetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun MoveTrackConsentScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "MoveTrack",
                onBack = onBack
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
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SmaStatusChip(
                    text = "OPTIONAL RESEARCH PROTOTYPE",
                    icon = Icons.Default.Science,
                    containerColor = SagePrimaryFixed,
                    contentColor = OnSagePrimaryFixed
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Your movement, your choice",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "An optional, camera-based movement guidance session designed around your physical comfort and personal autonomy.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnWarmStoneVariant,
                    lineHeight = 22.sp
                )
            }

            // Privacy Guarantee Box
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
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = OnOliveSecondaryContainer
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Your privacy comes first",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "The camera will never activate on this screen. Before any session starts, you decide what is processed, what is stored, and who can view your summaries.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnWarmStoneVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Ethics Shield 3 Pillars
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                EthicsShieldPillar(
                    icon = Icons.Default.CheckCircle,
                    title = "Strict Patient Autonomy",
                    description = "MoveTrack is strictly voluntary. Choosing not to participate does not affect your care plan or access to other features."
                )
                EthicsShieldPillar(
                    icon = Icons.Default.Memory,
                    title = "100% Local Processing",
                    description = "Vision analysis runs on your device. Video frames are analyzed transiently in RAM and never transmitted over the internet."
                )
                EthicsShieldPillar(
                    icon = Icons.Default.LockReset,
                    title = "Right to Withdraw Anytime",
                    description = "You can withdraw consent or delete saved preferences instantly from the Privacy Centre."
                )
            }

            // Honest Disclaimer Notice
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
                        tint = TerracottaTertiary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Clinical note: MoveTrack is experimental movement guidance, not a diagnostic medical device or substitute for clinical physiotherapy assessment.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            // Actions
            SmaPrimaryButton(
                text = "Review Informed Consent",
                leadingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = onContinue
            )

            SmaSecondaryButton(
                text = "Back to Dashboard",
                onClick = onBack
            )

            // Bottom clearance
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun EthicsShieldPillar(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SagePrimary,
                modifier = Modifier.size(22.dp).padding(top = 2.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnWarmStoneVariant,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
