package com.smamove.android.feature.careplan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun CareTaskDetailsScreen(
    taskId: Int,
    viewModel: CarePlanViewModel,
    onBack: () -> Unit
) {
    val task = viewModel.getTask(taskId) ?: viewModel.tasks.firstOrNull()

    if (task == null) {
        Box(
            modifier = Modifier.fillMaxSize().background(WarmStoneBackground),
            contentAlignment = Alignment.Center
        ) {
            Text("Task not found.")
        }
        return
    }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Care Task Protocol",
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Category & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmaStatusChip(
                    text = task.category,
                    icon = Icons.Default.Air,
                    containerColor = OliveSecondaryContainer,
                    contentColor = OnOliveSecondaryContainer
                )

                if (task.isCompleted) {
                    SmaStatusChip(
                        text = "Completed Today",
                        icon = Icons.Default.Check,
                        containerColor = SagePrimaryContainer,
                        contentColor = OnSagePrimaryContainer
                    )
                }
            }

            // Task Title Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnWarmStoneVariant,
                        lineHeight = 22.sp
                    )
                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.4f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = task.duration,
                                style = MaterialTheme.typography.labelMedium,
                                color = OnWarmStoneVariant
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = task.verifiedBy,
                                style = MaterialTheme.typography.labelMedium,
                                color = OnWarmStoneVariant
                            )
                        }
                    }
                }
            }

            // Clinician Demonstration Section Placeholder
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.OndemandVideo,
                            contentDescription = null,
                            tint = SagePrimary
                        )
                        Text(
                            text = "Clinician Demonstration",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(SurfaceContainerHigh, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(SagePrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play demonstration video",
                                    tint = OnSagePrimary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Text(
                                text = "Video Guide: ${task.verifiedBy}",
                                style = MaterialTheme.typography.labelMedium,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Standard safety procedure demonstration (3:45 mins)",
                                style = MaterialTheme.typography.bodySmall,
                                color = OutlineColor
                            )
                        }
                    }
                }
            }

            // Step-by-Step Protocol
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Step-by-Step Protocol",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )

                if (task.steps.isEmpty()) {
                    Text(
                        text = "Follow the standard procedure instructed by your physiotherapist.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnWarmStoneVariant
                    )
                } else {
                    task.steps.forEach { step ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(14.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(SagePrimary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${step.stepNumber}",
                                        color = OnSagePrimary,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = step.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = OnWarmStone
                                    )
                                    Text(
                                        text = step.instruction,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = OnWarmStoneVariant,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Safety & Medical Disclaimer
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = SurfaceContainerHigh
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = DeepOliveSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Do not force range of motion or increase pressures beyond your prescribed clinical threshold. Discontinue immediately if dizzy or experiencing respiratory distress.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            // Action Buttons
            if (task.isCompleted) {
                SmaSecondaryButton(
                    text = "Mark as Not Done",
                    onClick = {
                        viewModel.setTaskCompleted(task.id, false)
                    }
                )
            } else {
                SmaPrimaryButton(
                    text = "Mark Task Complete",
                    leadingIcon = Icons.Default.Check,
                    onClick = {
                        viewModel.setTaskCompleted(task.id, true)
                        onBack()
                    }
                )
            }

            // Bottom clearance
            Spacer(Modifier.height(48.dp))
        }
    }
}

