
package com.smamove.android.feature.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.feature.careplan.CarePlanViewModel

private val StitchBackground = Color(0xFFF8F9FF)
private val StitchSurface = Color(0xFFEFF4FF)
private val StitchBlue = Color(0xFFDCE9FF)
private val StitchPrimary = Color(0xFF00685F)
private val StitchTeal = Color(0xFF008378)
private val StitchInk = Color(0xFF0B1C30)
private val StitchMuted = Color(0xFF52616B)
private val StitchWhite = Color.White

@Composable
fun PatientDashboardScreen(
    carePlanViewModel: CarePlanViewModel,
    onOpenMoveTrack: () -> Unit = {},
    onOpenCarePlan: () -> Unit = {},
    onOpenJournal: () -> Unit = {},
    onOpenAppointments: () -> Unit = {},
    onOpenCareCircle: () -> Unit = {}
) {
    val tasks = carePlanViewModel.tasks
    val completedCount by remember {
        derivedStateOf { carePlanViewModel.completedCount }
    }

    val progress = if (tasks.isEmpty()) {
        0f
    } else {
        completedCount.toFloat() / tasks.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StitchBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Dashboard header
        Column {
            Text(
                text = "SMA-Move",
                color = StitchPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Good morning, Alex 👋",
                fontSize = 25.sp,
                color = StitchInk,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Your care journey, one day at a time.",
                color = StitchMuted,
                fontSize = 14.sp
            )
        }

        // Wellbeing Journal shortcut
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = StitchBlue
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "✦  DAILY WELLBEING",
                    color = StitchPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "How are you feeling today?",
                    fontSize = 21.sp,
                    color = StitchInk,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Record your mood, comfort level, and " +
                            "anything you would like to note.",
                    fontSize = 14.sp,
                    color = StitchMuted,
                    lineHeight = 21.sp
                )

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = onOpenJournal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StitchPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Open Wellbeing Journal")
                }
            }
        }

        // Care tasks
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = StitchWhite
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "Today's care tasks",
                    fontSize = 20.sp,
                    color = StitchInk,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "$completedCount of ${tasks.size} tasks completed",
                    fontSize = 14.sp,
                    color = StitchMuted
                )

                Spacer(Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = StitchTeal,
                    trackColor = StitchSurface
                )

                Spacer(Modifier.height(12.dp))

                tasks.take(3).forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 9.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = task.title,
                                color = StitchInk,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(Modifier.height(3.dp))

                            Text(
                                text = task.duration,
                                color = StitchMuted,
                                fontSize = 12.sp
                            )
                        }

                        Text(
                            text = if (task.isCompleted) {
                                "✓ Done"
                            } else {
                                "Pending"
                            },
                            color = if (task.isCompleted) {
                                StitchPrimary
                            } else {
                                StitchMuted
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onOpenCarePlan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "View all care tasks",
                        color = StitchInk
                    )
                }
            }
        }

        // MoveTrack
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = StitchSurface
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "MoveTrack",
                    fontSize = 21.sp,
                    color = StitchInk,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Optional camera-based movement sessions. " +
                            "This feature is being developed with privacy " +
                            "and patient consent in mind.",
                    color = StitchMuted,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = onOpenMoveTrack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StitchTeal,
                        contentColor = Color.White
                    )
                ) {
                    Text("Explore MoveTrack")
                }
            }
        }

        // Appointments placeholder
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = StitchWhite
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "Appointments",
                    fontSize = 20.sp,
                    color = StitchInk,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Keep your upcoming care appointments organised.",
                    color = StitchMuted,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onOpenAppointments,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("View appointments", color = StitchInk)
                }
            }
        }

        // CareCircle placeholder
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = StitchWhite
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "Your CareCircle",
                    fontSize = 20.sp,
                    color = StitchInk,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Patient-approved caregiver coordination.",
                    color = StitchMuted,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onOpenCareCircle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Open CareCircle", color = StitchInk)
                }
            }
        }

        Text(
            text = "Demo dashboard • Sample content only",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = StitchMuted,
            fontSize = 12.sp
        )
    }
}
