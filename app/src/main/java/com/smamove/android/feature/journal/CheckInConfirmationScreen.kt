package com.smamove.android.feature.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.theme.*

@Composable
fun CheckInConfirmationScreen(
    viewModel: JournalViewModel,
    onNavigateHome: () -> Unit,
    onNavigateCarePlan: () -> Unit
) {
    val latest = viewModel.latestEntry

    Scaffold(
        containerColor = WarmStoneBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Success Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(SagePrimaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success",
                    tint = OnSagePrimaryContainer,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Check-in Complete",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Your daily reflection has been recorded securely on your device for this session.",
                style = MaterialTheme.typography.bodyLarge,
                color = OnWarmStoneVariant,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(Modifier.height(28.dp))

            // Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recorded Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        SmaStatusChip(
                            text = "Saved Locally",
                            icon = Icons.Default.Lock,
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Energy Rating", color = OnWarmStoneVariant, style = MaterialTheme.typography.bodyMedium)
                        Text(latest?.mood ?: "Good", fontWeight = FontWeight.SemiBold, color = OnWarmStone)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Physical Comfort", color = OnWarmStoneVariant, style = MaterialTheme.typography.bodyMedium)
                        Text(latest?.comfortLevel ?: "Comfortable", fontWeight = FontWeight.SemiBold, color = OnWarmStone)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Fatigue Level", color = OnWarmStoneVariant, style = MaterialTheme.typography.bodyMedium)
                        Text(latest?.fatigueLevel ?: "Noticeable", fontWeight = FontWeight.SemiBold, color = OnWarmStone)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Recorded At", color = OnWarmStoneVariant, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = latest?.let { viewModel.formatDate(it.timestamp) } ?: "Just now",
                            fontWeight = FontWeight.SemiBold,
                            color = OnWarmStone
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            SmaPrimaryButton(
                text = "Back to Patient Dashboard",
                onClick = onNavigateHome
            )

            Spacer(Modifier.height(12.dp))

            SmaSecondaryButton(
                text = "View Care Plan Tasks",
                onClick = onNavigateCarePlan
            )
        }
    }
}

