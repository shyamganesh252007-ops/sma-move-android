
package com.smamove.android.feature.careplan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val CareBackground = Color(0xFFF8F9FF)
private val CareSurface = Color(0xFFEFF4FF)
private val CareBlue = Color(0xFFDCE9FF)
private val CareTeal = Color(0xFF008378)
private val CareInk = Color(0xFF0B1C30)
private val CareMuted = Color(0xFF3D4947)
private val CareWhite = Color.White

@Composable
fun CarePlanScreen(
    viewModel: CarePlanViewModel,
    onBack: () -> Unit
) {
    // CarePlanViewModel uses Compose snapshot state.
    val tasks = viewModel.tasks
    val completedCount by remember {
        derivedStateOf { viewModel.completedCount }
    }

    Scaffold(
        containerColor = CareBackground,
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
                        .size(44.dp)
                        .clickable { onBack() },
                    shape = CircleShape,
                    color = CareSurface
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("←", fontSize = 25.sp, color = CareInk)
                    }
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        "MY CARE PLAN",
                        fontSize = 11.sp,
                        letterSpacing = 1.2.sp,
                        color = CareTeal,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Today's care",
                        fontSize = 23.sp,
                        color = CareInk,
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
                color = CareBlue
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text(
                        "DEMO CARE PLAN",
                        color = CareTeal,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Example tasks for interface testing. " +
                                "Use your actual care team's instructions for care decisions.",
                        color = CareInk,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            // Progress card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = CareTeal
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "YOUR DAILY PROGRESS",
                        color = CareWhite.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            "$completedCount",
                            fontSize = 38.sp,
                            color = CareWhite,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "of ${tasks.size} tasks completed",
                            fontSize = 14.sp,
                            color = CareWhite
                        )
                    }

                    LinearProgressIndicator(
                        progress = {
                            if (tasks.isEmpty()) 0f
                            else completedCount.toFloat() / tasks.size
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = CareWhite,
                        trackColor = CareWhite.copy(alpha = 0.25f)
                    )

                    Text(
                        "Your progress updates when you check off a task.",
                        fontSize = 12.sp,
                        color = CareWhite
                    )
                }
            }

            Text(
                "Care tasks",
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                color = CareInk
            )

            tasks.forEach { task ->
                val done = task.isCompleted

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.toggleTask(task.id)
                        },
                    shape = RoundedCornerShape(17.dp),
                    color = if (done) CareSurface else CareWhite,
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
                            color = if (done) CareTeal else CareBlue
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    if (done) "✓" else "○",
                                    fontSize = 25.sp,
                                    color = if (done) CareWhite else CareTeal
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                task.title,
                                color = CareInk,
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
                                color = CareMuted,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            Surface(
                                shape = CircleShape,
                                color = CareBlue
                            ) {
                                Text(
                                    "${task.category} • ${task.duration}",
                                    modifier = Modifier.padding(
                                        horizontal = 9.dp,
                                        vertical = 5.dp
                                    ),
                                    color = CareTeal,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = CircleShape
            ) {
                Text("Back to dashboard")
            }
        }
    }
}
