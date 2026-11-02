package com.smamove.android.feature.caregiver

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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.feature.careplan.CarePlanViewModel
import com.smamove.android.ui.components.SmaOutlinedButton
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaregiverDashboardScreen(
    carePlanViewModel: CarePlanViewModel,
    onOpenSharedUpdates: () -> Unit,
    onOpenManageCaregivers: () -> Unit,
    onOpenTaskDetails: (Int) -> Unit,
    onSignOut: () -> Unit
) {
    val tasks = carePlanViewModel.tasks
    val completedCount by remember {
        derivedStateOf { carePlanViewModel.completedCount }
    }
    val progress = if (tasks.isEmpty()) 0f else completedCount.toFloat() / tasks.size

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Caregiver Companion",
                onOpenProfile = onSignOut
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
            // Patient Link Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
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
                                .background(OliveSecondaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "EV",
                                color = OnOliveSecondaryContainer,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Elena Vance",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                SmaStatusChip(
                                    text = "Demo Linked Profile",
                                    containerColor = SagePrimaryFixed,
                                    contentColor = OnSagePrimaryFixed
                                )
                            }
                            Text(
                                text = "Authorized Family View • Simulated adherence data",
                                style = MaterialTheme.typography.bodySmall,
                                color = OutlineColor
                            )
                        }
                    }
                }
            }

            // Daily Progress Overview
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SagePrimaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "DAILY OVERVIEW",
                                color = OnSagePrimaryContainer.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Care Plan Adherence",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = OnSagePrimaryContainer
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(SagePrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$completedCount/${tasks.size}",
                                color = OnSagePrimary,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "$completedCount of ${tasks.size} tasks checked off today by Elena. Next scheduled routine at 02:00 PM.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSagePrimaryContainer
                    )

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = OnSagePrimaryContainer,
                        trackColor = SagePrimary.copy(alpha = 0.4f)
                    )
                }
            }

            // Authorized Care Tasks Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Authorized Care Tasks",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                TextButton(onClick = onOpenSharedUpdates) {
                    Text("Activity Feed", color = SagePrimary, fontWeight = FontWeight.Bold)
                }
            }

            tasks.forEach { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (task.isCompleted) SurfaceContainer else SurfaceContainerLow
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        if (task.isCompleted) OliveSecondaryContainer else SurfaceContainerHigh,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (task.isCompleted) Icons.Default.Check else Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = if (task.isCompleted) OnOliveSecondaryContainer else OutlineColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.titleSmall,
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

                        if (task.isCompleted) {
                            SmaStatusChip(
                                text = "Done",
                                containerColor = SurfaceContainerHigh,
                                contentColor = OnWarmStoneVariant
                            )
                        } else {
                            OutlinedButton(
                                onClick = { onOpenTaskDetails(task.id) },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Assist", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }

            // Quick Actions: Shared Updates & Manage CareCircle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onOpenSharedUpdates,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Feed, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("Shared Updates")
                    }
                }

                OutlinedButton(
                    onClick = onOpenManageCaregivers,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Group, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("CareCircle")
                    }
                }
            }

            // Switch Role Button
            SmaSecondaryButton(
                text = "Switch Role / Back to Role Selection",
                leadingIcon = Icons.Default.Logout,
                onClick = onSignOut
            )

            // Bottom clearance so content is never clipped
            Spacer(Modifier.height(48.dp))
        }
    }
}

