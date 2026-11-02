package com.smamove.android.feature.caregiver

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

data class CareUpdateItem(
    val id: String,
    val author: String,
    val role: String,
    val title: String,
    val timestamp: String,
    val message: String,
    val icon: ImageVector,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedCareUpdatesScreen(
    onBack: () -> Unit
) {
    val updates = remember {
        mutableStateListOf(
            CareUpdateItem(
                id = "1",
                author = "Elena Vance",
                role = "Patient",
                title = "Morning Mobility Routine Completed",
                timestamp = "Today, 08:30 AM",
                message = "\"Felt steady during stretching. Slight shoulder stiffness improved after heat pack.\"",
                icon = Icons.Default.CheckCircle,
                category = "Patient"
            ),
            CareUpdateItem(
                id = "2",
                author = "Marcus Vance",
                role = "Caregiver",
                title = "Virtual Telehealth Visit Confirmed",
                timestamp = "Yesterday, 04:15 PM",
                message = "\"Confirmed Dr. Thorne appointment for 14 Nov 10:00 AM IST. Peak flow meter ready.\"",
                icon = Icons.Default.Event,
                category = "Caregivers"
            ),
            CareUpdateItem(
                id = "3",
                author = "Dr. Aris Thorne",
                role = "Clinician",
                title = "Care Plan Review Note",
                timestamp = "22 Oct 2026",
                message = "\"Adherence to respiratory protocol has been consistent. Maintain +15 cmH2O pressure.\"",
                icon = Icons.Default.MedicalServices,
                category = "Clinician"
            )
        )
    }

    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Patient", "Caregivers", "Clinician")

    var showAddNoteDialog by remember { mutableStateOf(false) }
    var newNoteText by remember { mutableStateOf("") }

    val filteredList = remember(selectedFilter, updates) {
        if (selectedFilter == "All") updates
        else updates.filter { it.category == selectedFilter }
    }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Shared Care Updates",
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Care Activity Timeline",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Chronological coordination feed of completed routines, notes, and clinician observations.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant
                )
            }

            // Quick Add Note Button
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
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(SagePrimaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                contentDescription = null,
                                tint = OnSagePrimaryContainer
                            )
                        }
                        Column {
                            Text(
                                text = "Post Care Note",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Share an update with your care circle",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }
                    }

                    Button(
                        onClick = { showAddNoteDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                    ) {
                        Text("Add Note")
                    }
                }
            }

            // Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SagePrimary,
                            selectedLabelColor = OnSagePrimary,
                            containerColor = SurfaceContainerLow,
                            labelColor = OnWarmStoneVariant
                        ),
                        shape = RoundedCornerShape(100.dp)
                    )
                }
            }

            // Timeline Items
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                filteredList.forEach { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(OliveSecondaryContainer, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = null,
                                            tint = OnOliveSecondaryContainer,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = item.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = OnWarmStone
                                        )
                                        Text(
                                            text = "${item.author} (${item.role})",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = SagePrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Text(
                                    text = item.timestamp,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OutlineColor
                                )
                            }

                            Surface(
                                color = WarmStoneBackground,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = item.message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnWarmStoneVariant,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddNoteDialog) {
        AlertDialog(
            onDismissRequest = { showAddNoteDialog = false },
            title = { Text("Add Care Update") },
            text = {
                OutlinedTextField(
                    value = newNoteText,
                    onValueChange = { newNoteText = it },
                    placeholder = { Text("Type update for caregivers and family...") },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newNoteText.isNotBlank()) {
                            updates.add(
                                0,
                                CareUpdateItem(
                                    id = System.currentTimeMillis().toString(),
                                    author = "Marcus Vance",
                                    role = "Caregiver",
                                    title = "Caregiver Update",
                                    timestamp = "Just now",
                                    message = "\"${newNoteText.trim()}\"",
                                    icon = Icons.Default.ChatBubbleOutline,
                                    category = "Caregivers"
                                )
                            )
                            newNoteText = ""
                            showAddNoteDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                ) {
                    Text("Post")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddNoteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

