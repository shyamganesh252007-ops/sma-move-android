
package com.smamove.android.feature.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val JournalBackground = Color(0xFFF8F9FF)
private val JournalSurface = Color(0xFFEFF4FF)
private val JournalBlue = Color(0xFFDCE9FF)
private val JournalTeal = Color(0xFF008378)
private val JournalInk = Color(0xFF0B1C30)
private val JournalMuted = Color(0xFF3D4947)
private val JournalWhite = Color.White

@Composable
fun WellbeingJournalScreen(
    viewModel: JournalViewModel,
    onBack: () -> Unit
) {
    var selectedMood by remember { mutableStateOf("Good") }
    var selectedComfort by remember { mutableStateOf("Comfortable") }
    var notes by remember { mutableStateOf("") }
    var saveMessage by remember { mutableStateOf<String?>(null) }

    val moods = listOf(
        "😴" to "Low",
        "🌤️" to "Fair",
        "✨" to "Good",
        "⚡" to "Great",
        "🚀" to "Peak"
    )

    val comfortOptions = listOf(
        "Comfortable",
        "Some discomfort",
        "Very uncomfortable"
    )

    Scaffold(
        containerColor = JournalBackground,
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
                    color = JournalSurface
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("←", fontSize = 25.sp, color = JournalInk)
                    }
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        "DAILY WELLBEING",
                        color = JournalTeal,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        "My journal",
                        color = JournalInk,
                        fontSize = 24.sp,
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
            // Privacy / demo notice
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = JournalBlue
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text(
                        "A SPACE FOR YOU",
                        color = JournalTeal,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Check in with yourself at your own pace. " +
                                "This is a demo journal; entries are currently kept " +
                                "in memory and are not sent to your care team.",
                        color = JournalInk,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            // Mood selector
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = JournalSurface
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        "How are you feeling today?",
                        color = JournalInk,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        "Choose the energy level that feels right for you.",
                        color = JournalMuted,
                        fontSize = 14.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        moods.forEach { (emoji, label) ->
                            val selected = selectedMood == label

                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(76.dp)
                                    .clickable {
                                        selectedMood = label
                                        saveMessage = null
                                    },
                                shape = RoundedCornerShape(13.dp),
                                color = if (selected) JournalWhite else JournalBackground,
                                border = if (selected) {
                                    androidx.compose.foundation.BorderStroke(
                                        2.dp, JournalTeal
                                    )
                                } else null
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(emoji, fontSize = 21.sp)
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        label,
                                        color = JournalInk,
                                        fontSize = 11.sp,
                                        fontWeight = if (selected) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Medium
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Comfort check-in
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = JournalWhite
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "How comfortable do you feel?",
                        color = JournalInk,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    comfortOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (selectedComfort == option) {
                                        JournalBlue
                                    } else {
                                        JournalBackground
                                    }
                                )
                                .clickable {
                                    selectedComfort = option
                                    saveMessage = null
                                }
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedComfort == option,
                                onClick = {
                                    selectedComfort = option
                                    saveMessage = null
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = JournalTeal
                                )
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                option,
                                color = JournalInk,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Journal notes
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Anything you'd like to note?",
                    color = JournalInk,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    "You can write about your day, comfort, or anything " +
                            "you'd like to remember.",
                    color = JournalMuted,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = {
                        notes = it
                        saveMessage = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 130.dp),
                    placeholder = {
                        Text("Write a note (optional)…")
                    },
                    shape = RoundedCornerShape(16.dp),
                    minLines = 4,
                    maxLines = 7,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = JournalTeal,
                        unfocusedBorderColor = JournalBlue,
                        focusedContainerColor = JournalWhite,
                        unfocusedContainerColor = JournalWhite
                    )
                )
            }

            if (saveMessage != null) {
                Text(
                    saveMessage!!,
                    color = JournalTeal,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = {
                    viewModel.saveEntry(
                        mood = selectedMood,
                        comfortLevel = selectedComfort,
                        notes = notes
                    )
                    notes = ""
                    saveMessage = "Your demo journal entry has been saved."
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = JournalTeal
                )
            ) {
                Text(
                    "Save wellbeing entry",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Previous entries
            Text(
                "Previous entries",
                color = JournalInk,
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold
            )

            if (viewModel.entries.isEmpty()) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = JournalSurface
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            "Your journal starts here",
                            color = JournalInk,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Text(
                            "Saved entries will appear here during this session.",
                            color = JournalMuted,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                viewModel.entries.forEach { entry ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = JournalSurface
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "${entry.mood} energy · ${entry.comfortLevel}",
                                color = JournalTeal,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                viewModel.formatDate(entry.timestamp),
                                color = JournalMuted,
                                fontSize = 12.sp
                            )

                            if (entry.notes.isNotBlank()) {
                                Text(
                                    entry.notes,
                                    color = JournalInk,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp
                                )
                            } else {
                                Text(
                                    "No additional notes.",
                                    color = JournalMuted,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
