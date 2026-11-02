package com.smamove.android.feature.appointments

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var reminderEnabled by remember { mutableStateOf(true) }
    var item1Checked by remember { mutableStateOf(true) }
    var item2Checked by remember { mutableStateOf(false) }
    var item3Checked by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Appointment Details",
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
            // Confirmed Session Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = OliveSecondaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SmaStatusChip(
                        text = "DEMO CONSULTATION (IST)",
                        icon = Icons.Default.Info,
                        containerColor = DeepOliveSecondary,
                        contentColor = OnDeepOliveSecondary
                    )

                    Text(
                        text = "Virtual Care Consultation",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = OnOliveSecondaryContainer
                    )

                    Text(
                        text = "Your secure clinical video room will open 10 minutes prior to scheduled session time.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnOliveSecondaryContainer.copy(alpha = 0.9f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Opening demo video room connection...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                        ) {
                            Text("Join Room")
                        }

                        OutlinedButton(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Appointment saved to device calendar (Demo)",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Add Calendar", color = OnWarmStone)
                        }
                    }
                }
            }

            // Clinician Core Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(SagePrimaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "AT",
                                    color = OnSagePrimaryContainer,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column {
                                Text(
                                    text = "Dr. Aris Thorne",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = "Pulmonology Specialist",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        SmaStatusChip(
                            text = "Specialist",
                            containerColor = SagePrimaryFixed,
                            contentColor = OnSagePrimaryFixed
                        )
                    }

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    // Date & Time
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(SurfaceContainerHigh, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Date & Time (IST)",
                                style = MaterialTheme.typography.labelSmall,
                                color = OutlineColor
                            )
                            Text(
                                text = "Thursday, 14 Nov 2026",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "10:00 AM – 10:45 AM Indian Standard Time (IST)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SagePrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Location
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(SurfaceContainerHigh, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Videocam,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Mode of Visit",
                                style = MaterialTheme.typography.labelSmall,
                                color = OutlineColor
                            )
                            Text(
                                text = "Encrypted Telehealth Session",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Secure link active 10 mins before visit",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }
                    }
                }
            }

            // Preparation Checklist
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Preparation Checklist",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    Text(
                        text = "Complete these preparation steps to assist your respiratory evaluation:",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant
                    )

                    PreparationCheckItem(
                        text = "Have latest peak flow and pulse oximeter readings nearby.",
                        checked = item1Checked,
                        onCheckedChange = { item1Checked = it }
                    )
                    PreparationCheckItem(
                        text = "Set up in a well-lit room with wheelchair neck support in place.",
                        checked = item2Checked,
                        onCheckedChange = { item2Checked = it }
                    )
                    PreparationCheckItem(
                        text = "List any changes in morning cough secretions over the last 14 days.",
                        checked = item3Checked,
                        onCheckedChange = { item3Checked = it }
                    )
                }
            }

            // Notification Reminder Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = SagePrimary
                        )
                        Column {
                            Text(
                                text = "Visit Reminder Alert",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Receive alert 1 hour prior on this device",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }
                    }

                    Switch(
                        checked = reminderEnabled,
                        onCheckedChange = { reminderEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = OnSagePrimary,
                            checkedTrackColor = SagePrimary
                        )
                    )
                }
            }

            // Disclaimer
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceContainerHigh,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Demo clinical appointment information for interface review. Real scheduling requires connecting to your hospital's ABDM / ABHA patient gateway.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnWarmStoneVariant,
                    modifier = Modifier.padding(14.dp)
                )
            }

            // Bottom clearance so content is never clipped
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun PreparationCheckItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(role = Role.Checkbox) { onCheckedChange(!checked) }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = SagePrimary)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (checked) OnWarmStone else OnWarmStoneVariant
        )
    }
}

