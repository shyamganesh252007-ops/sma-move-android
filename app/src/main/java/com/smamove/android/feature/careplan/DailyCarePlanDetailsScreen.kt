package com.smamove.android.feature.careplan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyCarePlanDetailsScreen(
    viewModel: CarePlanViewModel,
    onBack: () -> Unit,
    onOpenTaskDetails: (Int) -> Unit = {}
) {
    val tasks = viewModel.tasks
    val completedCount by remember {
        derivedStateOf { viewModel.completedCount }
    }
    val progress = if (tasks.isEmpty()) 0f else completedCount.toFloat() / tasks.size

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Care Plan Details",
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
            // Clinician Context Card
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(SagePrimaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "AT",
                                    color = OnSagePrimaryContainer,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column {
                                Text(
                                    text = "Dr. Aris Thorne",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = "Neuromuscular Specialist",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        SmaStatusChip(
                            text = "Demo Plan #4 (IST)",
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }

                    Surface(
                        color = WarmStoneBackground,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "DAILY ADHERENCE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OutlineColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$completedCount of ${tasks.size} tasks done",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(SagePrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${(progress * 100).toInt()}%",
                                    color = OnSagePrimary,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Morning Routine Section
            Text(
                text = "Morning Routine (09:00 AM - 12:00 PM)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            tasks.take(2).forEach { task ->
                CarePlanDetailRow(
                    task = task,
                    onToggle = { viewModel.toggleTask(task.id) },
                    onOpenDetails = { onOpenTaskDetails(task.id) }
                )
            }

            // Afternoon & Evening Routine Section
            Text(
                text = "Afternoon & Evening Routine (02:00 PM - 09:00 PM)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            tasks.drop(2).forEach { task ->
                CarePlanDetailRow(
                    task = task,
                    onToggle = { viewModel.toggleTask(task.id) },
                    onOpenDetails = { onOpenTaskDetails(task.id) }
                )
            }

            // Bottom clearance so content is never clipped
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun CarePlanDetailRow(
    task: CarePlanTask,
    onToggle: () -> Unit,
    onOpenDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) SurfaceContainer else SurfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onToggle,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (task.isCompleted) OliveSecondaryContainer else WarmStoneBackground,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (task.isCompleted) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Completed",
                                    tint = OnOliveSecondaryContainer,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Column {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "${task.scheduledTime} • ${task.duration} • ${task.category}",
                            style = MaterialTheme.typography.bodySmall,
                            color = OutlineColor
                        )
                    }
                }

                IconButton(onClick = onOpenDetails) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "View step instructions",
                        tint = SagePrimary
                    )
                }
            }

            // Clinician Note Snippet
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = WarmStoneBackground
            ) {
                Text(
                    text = "\"${task.description}\"",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnWarmStoneVariant,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

