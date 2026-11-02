package com.smamove.android.feature.movetrack

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaOutlinedButton
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoveTrackPrivacySettingsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val store = remember(context) { MoveTrackConsentStore(context) }

    var record by remember { mutableStateOf(store.getChoices()) }
    var showWithdrawDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Privacy Centre",
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
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SagePrimaryFixed)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(SagePrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = OnSagePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Privacy by Design • Local-First Storage",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnSagePrimaryFixed
                        )
                    }

                    Text(
                        text = "Your journal reflections, care-plan progress, and session settings are saved locally on this device. Designed with principles of digital personal data protection in mind; no telemetry is transmitted without your consent.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSagePrimaryFixedVariant,
                        lineHeight = 20.sp
                    )
                }
            }

            // Active Consents Section
            Text(
                text = "Active MoveTrack Consents",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Camera Consent Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                                    .background(OliveSecondaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = OnOliveSecondaryContainer
                                )
                            }
                            Column {
                                Text(
                                    text = "Camera Vision Tracking",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = if (record?.cameraOptIn == true) "Active: Consent granted" else "Inactive: Not opted in",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        if (record?.cameraOptIn == true) {
                            OutlinedButton(
                                onClick = {
                                    store.withdrawCameraConsent()
                                    record = store.getChoices()
                                    Toast.makeText(context, "Camera consent revoked.", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Revoke", color = ClinicalError, style = MaterialTheme.typography.labelSmall)
                            }
                        } else {
                            SmaStatusChip(
                                text = "Revoked / Off",
                                containerColor = SurfaceContainerHigh,
                                contentColor = OutlineColor
                            )
                        }
                    }

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    // Caregiver Sharing Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                                    .background(OliveSecondaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Group,
                                    contentDescription = null,
                                    tint = OnOliveSecondaryContainer
                                )
                            }
                            Column {
                                Text(
                                    text = "Caregiver Data Sharing",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = if (record?.caregiverSharingOptIn == true) "Configured (Local Demo)" else "Disabled",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        if (record?.caregiverSharingOptIn == true) {
                            OutlinedButton(
                                onClick = {
                                    store.withdrawCaregiverSharing()
                                    record = store.getChoices()
                                    Toast.makeText(context, "Caregiver sharing preference revoked.", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Revoke", color = ClinicalError, style = MaterialTheme.typography.labelSmall)
                            }
                        } else {
                            SmaStatusChip(
                                text = "Disabled",
                                containerColor = SurfaceContainerHigh,
                                contentColor = OutlineColor
                            )
                        }
                    }
                }
            }

            // Data Retention & Actions
            Text(
                text = "Local Storage & Data Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            Toast.makeText(context, "Local records exported to demo memory cache.", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh, contentColor = OnWarmStone)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = SagePrimary)
                            Text("Export Local Session Data")
                        }
                    }

                    Button(
                        onClick = { showWithdrawDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ClinicalErrorContainer, contentColor = OnClinicalErrorContainer)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = null, tint = ClinicalError)
                            Text("Withdraw All Consents & Clear Store")
                        }
                    }
                }
            }
        }
    }

    if (showWithdrawDialog) {
        AlertDialog(
            onDismissRequest = { showWithdrawDialog = false },
            title = { Text("Withdraw All Consent Choices?") },
            text = {
                Text("This will reset all MoveTrack camera and caregiver sharing preferences on this device. You will need to review consent again if you wish to participate.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        store.withdrawAllChoices()
                        record = store.getChoices()
                        showWithdrawDialog = false
                        Toast.makeText(context, "All MoveTrack preferences have been reset.", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ClinicalError)
                ) {
                    Text("Confirm Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showWithdrawDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
