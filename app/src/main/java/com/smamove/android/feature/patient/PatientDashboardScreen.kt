package com.smamove.android.feature.patient

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.feature.careplan.CarePlanTask
import com.smamove.android.feature.careplan.CarePlanViewModel
import com.smamove.android.feature.journal.JournalViewModel
import com.smamove.android.ui.components.*
import com.smamove.android.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDashboardScreen(
    carePlanViewModel: CarePlanViewModel,
    journalViewModel: JournalViewModel,
    onOpenMoveTrack: () -> Unit = {},
    onOpenMoveTrackPrivacy: () -> Unit = {},
    onOpenCarePlan: () -> Unit = {},
    onOpenTaskDetails: (Int) -> Unit = {},
    onOpenJournal: () -> Unit = {},
    onOpenAppointments: () -> Unit = {},
    onOpenProfile: () -> Unit = {},
    onOpenAccessibility: () -> Unit = {},
    onOpenPrivacy: () -> Unit = {}
) {
    val tasks = carePlanViewModel.tasks
    val completedCount by remember {
        derivedStateOf { carePlanViewModel.completedCount }
    }
    val progress = if (tasks.isEmpty()) 0f else completedCount.toFloat() / tasks.size
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "DashboardProgress"
    )
    val progressPercent = (animatedProgress * 100).toInt()

    val currentDateStr = remember {
        SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH).format(Date())
    }

    var showAssistanceDialog by remember { mutableStateOf(false) }
    var assistanceNote by remember { mutableStateOf("") }
    var noteSentNotification by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "SMA-Move",
                onOpenAccessibility = onOpenAccessibility,
                onOpenPrivacy = onOpenPrivacy,
                onOpenProfile = onOpenProfile
            )
        },
        bottomBar = {
            SmaBottomNavigationBar(
                currentDestination = SmaNavDestination.HOME,
                onNavigate = { dest ->
                    when (dest) {
                        SmaNavDestination.HOME -> {}
                        SmaNavDestination.CARE_PLAN -> onOpenCarePlan()
                        SmaNavDestination.WELLBEING -> onOpenJournal()
                        SmaNavDestination.PROFILE -> onOpenProfile()
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Greeting & Date Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentDateStr.uppercase(Locale.ENGLISH),
                        style = MaterialTheme.typography.labelSmall,
                        color = OutlineColor,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Hello, Elena",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                }
                SmaStatusChip(
                    text = "Demo Patient Profile",
                    icon = Icons.Default.Person,
                    containerColor = OliveSecondaryContainer,
                    contentColor = OnOliveSecondaryContainer
                )
            }

            // Animated notification message if assistance note was submitted
            AnimatedVisibility(
                visible = noteSentNotification != null,
                enter = fadeIn(tween(250)) + expandVertically(tween(250)),
                exit = fadeOut(tween(200)) + shrinkVertically(tween(200))
            ) {
                if (noteSentNotification != null) {
                    Surface(
                        color = OliveSecondaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = OnOliveSecondaryContainer
                            )
                            Text(
                                text = noteSentNotification!!,
                                color = OnOliveSecondaryContainer,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Daily Care Plan Progress Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(role = Role.Button, onClick = onOpenCarePlan)
                    .semantics {
                        contentDescription = "Daily care plan progress card. $completedCount of ${tasks.size} tasks completed, $progressPercent percent."
                    },
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
                        Column {
                            Text(
                                text = "Daily Care Plan",
                                color = SagePrimary,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "$completedCount of ${tasks.size} tasks completed",
                                color = OnWarmStone,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Circular Progress Badge
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(SagePrimaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$progressPercent%",
                                color = OnSagePrimaryContainer,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Linear progress indicator
                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = SagePrimary,
                        trackColor = SurfaceContainerHigh
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tap to open full care plan",
                            color = OutlineColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = OutlineColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // MoveTrack Session Card (Experimental)
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
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(SagePrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = OnSagePrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "MoveTrack Session",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = OnWarmStone
                                    )
                                    SmaStatusChip(
                                        text = "Experimental",
                                        containerColor = DeepOliveSecondary,
                                        contentColor = OnDeepOliveSecondary
                                    )
                                }
                                Text(
                                    text = "Camera-based gentle movement support",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }
                    }

                    Text(
                        text = "Participate in an optional posture and mobility check using on-device vision guidance. No video recordings leave your device. Model predictions are for exploration only.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnWarmStoneVariant,
                        lineHeight = 20.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onOpenMoveTrack,
                            modifier = Modifier
                                .weight(1.2f)
                                .heightIn(min = 48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SagePrimary,
                                contentColor = OnSagePrimary
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text("Start Session", fontWeight = FontWeight.SemiBold)
                            }
                        }

                        OutlinedButton(
                            onClick = onOpenMoveTrackPrivacy,
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 48.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Consent & Info", color = OnWarmStone, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            // Today's Care Tasks Section
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Tasks",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    val remaining = tasks.count { !it.isCompleted }
                    Text(
                        text = "$remaining remaining",
                        style = MaterialTheme.typography.labelMedium,
                        color = OutlineColor
                    )
                }

                tasks.take(3).forEach { task ->
                    CareTaskDashboardRow(
                        task = task,
                        onToggle = { carePlanViewModel.toggleTask(task.id) },
                        onOpenDetails = { onOpenTaskDetails(task.id) }
                    )
                }

                SmaOutlinedButton(
                    text = "View All Care Tasks (${tasks.size})",
                    onClick = onOpenCarePlan
                )
            }

            // Quick Entry Points (Wellbeing & Appointment)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Wellbeing Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(role = Role.Button, onClick = onOpenJournal),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(OliveSecondaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = OnOliveSecondaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Wellbeing Check-in",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "Log mood, comfort & symptoms",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnWarmStoneVariant
                        )
                    }
                }

                // Appointment Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(role = Role.Button, onClick = onOpenAppointments),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(SagePrimaryFixed, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = null,
                                tint = OnSagePrimaryFixed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Appointments",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "Dr. Aris Thorne (Pulmonology)",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnWarmStoneVariant
                        )
                    }
                }
            }

            // Quick Assistance or Note Trigger
            SmaSecondaryButton(
                text = "Add Clinical Note or Request Assistance",
                leadingIcon = Icons.Default.EditNote,
                onClick = { showAssistanceDialog = true }
            )

            // Honest Prototype Disclaimer Footer
            Text(
                text = "SMA-Move prototype · Native Jetpack Compose · Indian localization (IST)",
                style = MaterialTheme.typography.bodySmall,
                color = OutlineColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Bottom clearance so task cards are not clipped by bottom navigation
            Spacer(Modifier.height(48.dp))
        }
    }

    // Assistance Note Dialog
    if (showAssistanceDialog) {
        AlertDialog(
            modifier = Modifier.imePadding(),
            onDismissRequest = { showAssistanceDialog = false },
            title = {
                Text(
                    text = "Care Team Note & Assistance",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Send a direct observation or request assistance from your primary care coordinator.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnWarmStoneVariant
                    )
                    OutlinedTextField(
                        value = assistanceNote,
                        onValueChange = { assistanceNote = it },
                        placeholder = { Text("Describe observation, comfort or assistance needed...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        noteSentNotification = "Note logged successfully for your care team."
                        showAssistanceDialog = false
                        assistanceNote = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                ) {
                    Text("Send Note")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAssistanceDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun CareTaskDashboardRow(
    task: CarePlanTask,
    onToggle: () -> Unit,
    onOpenDetails: () -> Unit
) {
    val cardBg by animateColorAsState(
        targetValue = if (task.isCompleted) SurfaceContainer else SurfaceContainerLow,
        animationSpec = tween(durationMillis = 200),
        label = "DashboardRowBg"
    )
    val checkBg by animateColorAsState(
        targetValue = if (task.isCompleted) OliveSecondaryContainer else WarmStoneBackground,
        animationSpec = tween(durationMillis = 200),
        label = "DashboardCheckBg"
    )
    val checkmarkAlpha by animateFloatAsState(
        targetValue = if (task.isCompleted) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "DashboardCheckAlpha"
    )
    val checkmarkScale by animateFloatAsState(
        targetValue = if (task.isCompleted) 1f else 0.5f,
        animationSpec = tween(durationMillis = 200),
        label = "DashboardCheckScale"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Accessible Toggle Button (Min 48dp x 48dp)
            IconButton(
                onClick = onToggle,
                modifier = Modifier
                    .size(48.dp)
                    .semantics {
                        contentDescription = "Toggle task: ${task.title}"
                        stateDescription = if (task.isCompleted) "Completed" else "Not completed"
                        role = Role.Checkbox
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(checkBg),
                    contentAlignment = Alignment.Center
                ) {
                    if (checkmarkAlpha > 0.01f) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = OnOliveSecondaryContainer,
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer {
                                    alpha = checkmarkAlpha
                                    scaleX = checkmarkScale
                                    scaleY = checkmarkScale
                                }
                        )
                    }
                }
            }

            // Task Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(role = Role.Button, onClick = onOpenDetails)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmaStatusChip(
                        text = task.category,
                        containerColor = if (task.isCompleted) SurfaceContainerHigh else SagePrimaryFixed,
                        contentColor = if (task.isCompleted) OnWarmStoneVariant else OnSagePrimaryFixed
                    )
                    Text(
                        text = task.scheduledTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = OutlineColor
                    )
                    if (task.isCompleted) {
                        SmaStatusChip(
                            text = "Done",
                            icon = Icons.Default.Check,
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }
                }

                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (task.isCompleted) OnWarmStoneVariant else OnWarmStone,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Right Chevron to View Details
            IconButton(
                onClick = onOpenDetails,
                modifier = Modifier
                    .size(48.dp)
                    .semantics { contentDescription = "View details for ${task.title}" }
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = OutlineColor
                )
            }
        }
    }
}
