package com.smamove.android.feature.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.*
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellbeingJournalScreen(
    viewModel: JournalViewModel,
    onBack: () -> Unit,
    onOpenSymptomNotes: () -> Unit = {},
    onCheckInConfirmed: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateCarePlan: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    var selectedEnergy by remember { mutableStateOf("Steady") }
    var selectedFatigue by remember { mutableStateOf("Noticeable") }
    var selectedComfort by remember { mutableStateOf("Comfortable") }
    var notes by remember { mutableStateOf("") }
    var saveFeedback by remember { mutableStateOf<String?>(null) }

    val energyOptions = listOf("Low", "Mild", "Steady", "Good", "High")
    val fatigueOptions = listOf("Minimal", "Noticeable", "Heavy", "Severe")
    val comfortOptions = listOf("Comfortable", "Some discomfort", "Assistance needed")

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Wellbeing Journal",
                onBack = onBack
            )
        },
        bottomBar = {
            SmaBottomNavigationBar(
                currentDestination = SmaNavDestination.WELLBEING,
                onNavigate = { dest ->
                    when (dest) {
                        SmaNavDestination.HOME -> onNavigateHome()
                        SmaNavDestination.CARE_PLAN -> onNavigateCarePlan()
                        SmaNavDestination.WELLBEING -> {}
                        SmaNavDestination.PROFILE -> onNavigateProfile()
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Section
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "DAILY REFLECTION",
                    color = SagePrimary,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "How are you feeling today?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Check in with your body. Observations are stored locally on this device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant
                )
            }

            // Energy Level Selector
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Energy Level",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                        }

                        SmaStatusChip(
                            text = selectedEnergy,
                            containerColor = SagePrimaryFixed,
                            contentColor = OnSagePrimaryFixed
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        energyOptions.forEach { energy ->
                            val isSelected = selectedEnergy == energy
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clickable(role = Role.RadioButton) { selectedEnergy = energy },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) SagePrimaryContainer else SurfaceContainerHigh
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = energy,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) OnSagePrimaryContainer else OnWarmStone
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Fatigue Level Selector
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = TerracottaTertiary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Fatigue Level",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                        }

                        SmaStatusChip(
                            text = selectedFatigue,
                            containerColor = TerracottaTertiaryFixed,
                            contentColor = OnTerracottaTertiaryFixed
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        fatigueOptions.forEach { fatigue ->
                            val isSelected = selectedFatigue == fatigue
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clickable(role = Role.RadioButton) { selectedFatigue = fatigue },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) TerracottaTertiaryContainer else SurfaceContainerHigh
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = fatigue,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) OnTerracottaTertiaryContainer else OnWarmStone
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Comfort Level Radio Group
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Physical Comfort",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )

                    comfortOptions.forEach { option ->
                        val isSelected = selectedComfort == option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) OliveSecondaryContainer else WarmStoneBackground)
                                .clickable(role = Role.RadioButton) { selectedComfort = option }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedComfort = option },
                                colors = RadioButtonDefaults.colors(selectedColor = DeepOliveSecondary)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) OnOliveSecondaryContainer else OnWarmStone
                            )
                        }
                    }
                }
            }

            // Reflection Notes Field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Personal Reflection (Optional)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("Write about your breathing, sleep, or posture today...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp),
                    minLines = 3,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SagePrimary,
                        unfocusedBorderColor = OutlineVariantColor
                    )
                )
            }

            // Deep Symptom Logger Link
            SmaOutlinedButton(
                text = "Detailed Symptom & Mobility Notes",
                leadingIcon = Icons.Default.Healing,
                onClick = onOpenSymptomNotes
            )

            // Save Reflection Button
            SmaPrimaryButton(
                text = "Save Today's Reflection",
                leadingIcon = Icons.Default.Check,
                onClick = {
                    viewModel.saveEntry(
                        mood = selectedEnergy,
                        comfortLevel = selectedComfort,
                        notes = notes,
                        fatigueLevel = selectedFatigue
                    )
                    notes = ""
                    onCheckInConfirmed()
                }
            )

            // Previous Entries Section
            Text(
                text = "Recent Journal Records",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            if (viewModel.entries.isEmpty()) {
                Text(
                    text = "No saved check-ins yet. Start by saving your reflection above.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OutlineColor
                )
            } else {
                viewModel.entries.forEach { entry ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    SmaStatusChip(
                                        text = "${entry.mood} Energy",
                                        containerColor = SagePrimaryFixed,
                                        contentColor = OnSagePrimaryFixed
                                    )
                                    SmaStatusChip(
                                        text = entry.comfortLevel,
                                        containerColor = OliveSecondaryContainer,
                                        contentColor = OnOliveSecondaryContainer
                                    )
                                }
                                Text(
                                    text = viewModel.formatDate(entry.timestamp),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OutlineColor
                                )
                            }
                            if (entry.notes.isNotBlank()) {
                                Text(
                                    text = entry.notes,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnWarmStone
                                )
                            }
                            if (entry.symptomNotes.isNotBlank()) {
                                Text(
                                    text = "Symptom Note: ${entry.symptomNotes}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TerracottaTertiary
                                )
                            }
                        }
                    }
                }
            }

            // Bottom clearance so journal records are never clipped by bottom navigation
            Spacer(Modifier.height(48.dp))
        }
    }
}
