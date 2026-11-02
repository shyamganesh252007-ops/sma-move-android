package com.smamove.android.feature.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellbeingJournalSymptomScreen(
    viewModel: JournalViewModel,
    onBack: () -> Unit,
    onCheckInConfirmed: () -> Unit
) {
    var upperBackTension by remember { mutableStateOf("Mild tension") }
    var respiratoryComfort by remember { mutableStateOf("Comfortable") }
    var mobilityEase by remember { mutableStateOf("Normal baseline") }
    var assistanceNeeded by remember { mutableStateOf(false) }
    var symptomNotes by remember { mutableStateOf("") }

    val tensionOptions = listOf("None", "Mild tension", "Moderate ache", "Significant discomfort")
    val respiratoryOptions = listOf("Comfortable", "Mild secretions", "Shortness of breath", "Need cough assist")
    val mobilityOptions = listOf("Feeling stronger", "Normal baseline", "More fatigued than usual")

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Symptom & Mobility Check",
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
            // Header Info Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = OliveSecondaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = OnOliveSecondaryContainer
                    )
                    Text(
                        text = "Detailed symptom observations help your physiotherapist track respiratory clearance and posture trends.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnOliveSecondaryContainer
                    )
                }
            }

            // Upper Back & Shoulders
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Upper Body & Neck Tension",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    tensionOptions.forEach { option ->
                        val isSelected = upperBackTension == option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) OliveSecondaryContainer else WarmStoneBackground)
                                .clickable(role = Role.RadioButton) { upperBackTension = option }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { upperBackTension = option },
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

            // Respiratory Comfort
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Respiratory & Airway Ease",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    respiratoryOptions.forEach { option ->
                        val isSelected = respiratoryComfort == option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) OliveSecondaryContainer else WarmStoneBackground)
                                .clickable(role = Role.RadioButton) { respiratoryComfort = option }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { respiratoryComfort = option },
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

            // Mobility Baseline
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Mobility & Dexterity Baseline",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    mobilityOptions.forEach { option ->
                        val isSelected = mobilityEase == option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) OliveSecondaryContainer else WarmStoneBackground)
                                .clickable(role = Role.RadioButton) { mobilityEase = option }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { mobilityEase = option },
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

            // Assistance Needed Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Assistance Needed Today",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "Flag for caregiver review during afternoon transfer",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnWarmStoneVariant
                        )
                    }
                    Switch(
                        checked = assistanceNeeded,
                        onCheckedChange = { assistanceNeeded = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = OnSagePrimary,
                            checkedTrackColor = SagePrimary
                        )
                    )
                }
            }

            // Freeform Symptom Notes
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Specific Clinical or Posture Notes",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                OutlinedTextField(
                    value = symptomNotes,
                    onValueChange = { symptomNotes = it },
                    placeholder = { Text("Note any muscle fatigue, seating discomfort, or changes...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp),
                    minLines = 3,
                    shape = RoundedCornerShape(14.dp)
                )
            }

            // Submit Button
            SmaPrimaryButton(
                text = "Save Symptoms & Confirm Check-In",
                leadingIcon = Icons.Default.Check,
                onClick = {
                    viewModel.saveEntry(
                        mood = mobilityEase,
                        comfortLevel = respiratoryComfort,
                        notes = symptomNotes,
                        symptomNotes = "$upperBackTension; $respiratoryComfort",
                        fatigueLevel = if (mobilityEase.contains("fatigued")) "Heavy" else "Moderate",
                        mobilityAssistanceNeeded = assistanceNeeded
                    )
                    onCheckInConfirmed()
                }
            )
        }
    }
}

