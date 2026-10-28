
package com.smamove.android.feature.careplan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CarePlanScreen(
    viewModel: CarePlanViewModel,
    onBack: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val tasks = viewModel.tasks
    val completedCount by remember {
        derivedStateOf { viewModel.completedCount }
    }
    val progress = if (tasks.isEmpty()) {
        0f
    } else {
        completedCount.toFloat() / tasks.size
    }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 180),
        label = "care-plan-progress"
    )

    Scaffold(
        containerColor = colors.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(role = Role.Button) { onBack() }
                        .semantics {
                            contentDescription = "Go back"
                        },
                    shape = CircleShape,
                    color = colors.surfaceVariant
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("←", fontSize = 25.sp, color = colors.onSurface)
                    }
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        "MY CARE PLAN",
                        fontSize = 11.sp,
                        letterSpacing = 1.2.sp,
                        color = colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Today's care",
                        fontSize = 23.sp,
                        color = colors.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Demo notice
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = colors.secondaryContainer
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text(
                        "DEMO CARE PLAN",
                        color = colors.primary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Example tasks for interface testing. " +
                                "Use your actual care team's instructions for care decisions.",
                        color = colors.onSecondaryContainer,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            // Progress card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = colors.primary
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "YOUR DAILY PROGRESS",
                        color = colors.onPrimary.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )

                    if (tasks.isEmpty()) {
                        Text(
                            "No tasks are available in this plan yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.onPrimary
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                "$completedCount",
                                fontSize = 38.sp,
                                color = colors.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "of ${tasks.size} example tasks checked off",
                                fontSize = 14.sp,
                                color = colors.onPrimary
                            )
                        }

                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = colors.onPrimary,
                            trackColor = colors.onPrimary.copy(alpha = 0.25f)
                        )

                        Text(
                            "There’s no need to complete everything at once.",
                            fontSize = 12.sp,
                            color = colors.onPrimary
                        )
                    }
                }
            }

            Text(
                "Care tasks",
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.onSurface
            )

            tasks.forEach { task ->
                val done = task.isCompleted

                key(task.id) {
                val taskSurfaceColor by animateColorAsState(
                    targetValue = if (done) {
                        colors.secondaryContainer
                    } else {
                        colors.surface
                    },
                    animationSpec = tween(durationMillis = 160),
                    label = "care-task-surface-${task.id}"
                )
                val taskIconColor by animateColorAsState(
                    targetValue = if (done) colors.onPrimary else colors.primary,
                    animationSpec = tween(durationMillis = 160),
                    label = "care-task-icon-${task.id}"
                )
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .toggleable(
                            value = done,
                            role = Role.Checkbox,
                            onValueChange = { viewModel.toggleTask(task.id) }
                        )
                        .semantics {
                            stateDescription = if (done) "Complete" else "Not complete"
                        },
                    shape = RoundedCornerShape(17.dp),
                    color = taskSurfaceColor,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            color = if (done) colors.primary else colors.secondaryContainer
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    if (done) "✓" else "○",
                                    fontSize = 25.sp,
                                    color = taskIconColor
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                task.title,
                                color = colors.onSurface,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = if (done) {
                                    TextDecoration.LineThrough
                                } else {
                                    TextDecoration.None
                                }
                            )

                            Text(
                                task.description,
                                color = colors.onSurfaceVariant,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            Surface(
                                shape = CircleShape,
                                color = colors.secondaryContainer
                            ) {
                                Text(
                                    "${task.category} • ${task.duration}",
                                    modifier = Modifier.padding(
                                        horizontal = 9.dp,
                                        vertical = 5.dp
                                    ),
                                    color = colors.primary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                }
            }

            if (tasks.isEmpty()) {
                Text(
                    text = "No care tasks are available yet.",
                    color = colors.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
                shape = CircleShape
            ) {
                Text("Back to dashboard")
            }
        }
    }
}
