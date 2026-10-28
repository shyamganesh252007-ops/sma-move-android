package com.smamove.android.feature.patient

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smamove.android.feature.careplan.CarePlanTask
import com.smamove.android.feature.careplan.CarePlanViewModel
import com.smamove.android.feature.journal.JournalViewModel

@Composable
fun PatientDashboardScreen(
    carePlanViewModel: CarePlanViewModel,
    journalViewModel: JournalViewModel,
    onOpenMoveTrack: () -> Unit = {},
    onOpenCarePlan: () -> Unit = {},
    onOpenJournal: () -> Unit = {},
    onOpenMoveTrackPrivacy: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    val tasks = carePlanViewModel.tasks
    val completedCount by remember {
        derivedStateOf { carePlanViewModel.completedCount }
    }
    val entries = journalViewModel.entries
    val latestEntry = entries.firstOrNull()
    val progress = if (tasks.isEmpty()) 0f else completedCount.toFloat() / tasks.size
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 180),
        label = "dashboard-care-progress"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background
        ) {}

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 720.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "SMA-MOVE  /  TODAY",
                    color = colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Welcome back",
                    color = colorScheme.onBackground,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "Your care, at your pace.",
                    color = colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            CarePlanOverview(
                tasks = tasks,
                completedCount = completedCount,
                progress = animatedProgress,
                onOpenCarePlan = onOpenCarePlan
            )

            WellbeingOverview(
                latestEntryLabel = latestEntry?.let {
                    journalViewModel.formatDate(it.timestamp)
                },
                onOpenJournal = onOpenJournal
            )

            HorizontalDivider(color = colorScheme.outlineVariant)

            MoveTrackOverview(
                onOpenMoveTrack = onOpenMoveTrack,
                onOpenPrivacySettings = onOpenMoveTrackPrivacy
            )

            UnavailableFeatures()
        }
    }
}

@Composable
private fun CarePlanOverview(
    tasks: List<CarePlanTask>,
    completedCount: Int,
    progress: Float,
    onOpenCarePlan: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = colorScheme.surface,
        border = BorderStroke(1.dp, colorScheme.outlineVariant),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Care plan",
                    color = colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "SAMPLE TASKS",
                    color = colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (tasks.isEmpty()) {
                Text(
                    text = "No care tasks are available yet.",
                    color = colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = "$completedCount of ${tasks.size} example tasks checked off",
                    color = colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            contentDescription =
                                "$completedCount of ${tasks.size} example care-plan tasks checked off"
                        },
                    color = colorScheme.primary,
                    trackColor = colorScheme.surfaceVariant
                )

                tasks.take(3).forEach { task ->
                    key(task.id) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = task.title,
                                color = colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "${task.category} · ${task.duration}",
                                color = colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = if (task.isCompleted) "Checked off" else "Not yet",
                            color = if (task.isCompleted) {
                                colorScheme.primary
                            } else {
                                colorScheme.onSurfaceVariant
                            },
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    }
                }
            }

            OutlinedButton(
                onClick = onOpenCarePlan,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Open full care plan")
            }
        }
    }
}

@Composable
private fun WellbeingOverview(
    latestEntryLabel: String?,
    onOpenJournal: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "Wellbeing check-in",
                    color = colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = latestEntryLabel?.let { "Last saved $it" }
                        ?: "Whenever you’re ready, you can check in.",
                    color = colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(
                onClick = onOpenJournal,
                modifier = Modifier.heightIn(min = 48.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(if (latestEntryLabel == null) "Check in" else "Open")
            }
        }
    }
}

@Composable
private fun MoveTrackOverview(
    onOpenMoveTrack: () -> Unit,
    onOpenPrivacySettings: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "MoveTrack",
            color = colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Optional research prototype. It does not provide a diagnosis or a clinically validated assessment.",
            color = colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = onOpenMoveTrack,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Review consent information")
        }
        OutlinedButton(
            onClick = onOpenPrivacySettings,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("MoveTrack privacy settings")
        }
    }
}

@Composable
private fun UnavailableFeatures() {
    val colorScheme = MaterialTheme.colorScheme

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Not available in this prototype",
            color = colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium
        )
        UnavailableFeatureRow(
            title = "Appointments and reminders",
            detail = "No appointment source or reminder scheduling is connected."
        )
        UnavailableFeatureRow(
            title = "CareCircle",
            detail = "Caregiver accounts and record sharing are not implemented."
        )
    }
}

@Composable
private fun UnavailableFeatureRow(
    title: String,
    detail: String
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = title,
            color = colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = detail,
            color = colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
