package com.smamove.android.feature.movetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoveTrackSessionSummaryScreen(
    onNavigateHome: () -> Unit
) {
    val dateStr = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH).format(Date())

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Session Summary",
                onBack = onNavigateHome
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
            // Success Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = OliveSecondaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SmaStatusChip(
                        text = "SIMULATED EVALUATION SESSION",
                        icon = Icons.Default.Science,
                        containerColor = DeepOliveSecondary,
                        contentColor = OnDeepOliveSecondary
                    )

                    Text(
                        text = "Posture Guidance Preview",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = OnOliveSecondaryContainer
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = OnOliveSecondaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = dateStr,
                                style = MaterialTheme.typography.bodySmall,
                                color = OnOliveSecondaryContainer
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = OnOliveSecondaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "6 mins 30 secs",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnOliveSecondaryContainer
                            )
                        }
                    }
                }
            }

            // Session Metrics
            Text(
                text = "Observed Movement Indicators",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            // Metric 1: Posture Stability
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Posture Stability Consistency",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        SmaStatusChip(
                            text = "Steady",
                            containerColor = SagePrimaryFixed,
                            contentColor = OnSagePrimaryFixed
                        )
                    }

                    Text(
                        text = "Upright head and trunk alignment maintained consistently during guided breathing cycles.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant
                    )

                    LinearProgressIndicator(
                        progress = { 0.88f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = SagePrimary,
                        trackColor = SurfaceContainerHigh
                    )

                    Text(
                        text = "88% consistency across recorded checkpoints",
                        style = MaterialTheme.typography.bodySmall,
                        color = OutlineColor,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            // Metric 2: Range of Motion
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Gentle Stretch Cycles",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        SmaStatusChip(
                            text = "3 of 3 Completed",
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }

                    Text(
                        text = "Slow shoulder elevation and assisted neck rotations performed without reported pain.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant
                    )
                }
            }

            // Clinical Non-Diagnostic Notice
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
                        tint = DeepOliveSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Notice: These metrics represent sample simulation data for UI evaluation. A live camera session has not run. Production movement estimation models are currently in clinical research and require regulatory clearance. No video has been captured, stored, or transmitted.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            // Action
            SmaPrimaryButton(
                text = "Return to Dashboard",
                leadingIcon = Icons.Default.Check,
                onClick = onNavigateHome
            )

            Spacer(Modifier.height(36.dp))
        }
    }
}

