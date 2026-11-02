package com.smamove.android.feature.movetrack

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoveTrackConsentOptionsScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val store = remember(context) { MoveTrackConsentStore(context) }

    var cameraOptIn by remember { mutableStateOf(false) }
    var caregiverSharingOptIn by remember { mutableStateOf(false) }
    var loaded by remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraOptIn = true
            Toast.makeText(context, "Android camera permission granted.", Toast.LENGTH_SHORT).show()
        } else {
            cameraOptIn = false
            Toast.makeText(
                context,
                "Camera permission was denied. Android runtime permission is required for vision guidance.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        val currentChoices = store.getChoices()
        if (currentChoices != null) {
            cameraOptIn = currentChoices.cameraOptIn
            caregiverSharingOptIn = currentChoices.caregiverSharingOptIn
        }
        loaded = true
    }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Consent Preferences",
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
                SmaStatusChip(
                    text = "STEP 3 OF 3: PREFERENCES",
                    icon = Icons.Default.Tune,
                    containerColor = SagePrimaryFixed,
                    contentColor = OnSagePrimaryFixed
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Select your participation choices",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Configure each option independently. You can change or withdraw these choices at any time in the Privacy Centre.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant,
                    lineHeight = 20.sp
                )
            }

            // Option 1: Camera Vision Processing
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
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(SagePrimaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = OnSagePrimaryContainer
                                )
                            }
                            Column {
                                Text(
                                    text = "Camera Vision Processing",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = "Enable on-device skeletal tracking during voluntary movement sessions.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        Switch(
                            checked = cameraOptIn,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    val currentPermission = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    )
                                    if (currentPermission == PackageManager.PERMISSION_GRANTED) {
                                        cameraOptIn = true
                                    } else {
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                } else {
                                    cameraOptIn = false
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnSagePrimary,
                                checkedTrackColor = SagePrimary
                            )
                        )
                    }

                    Surface(
                        color = WarmStoneBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Consent and Android runtime permission are both required before any camera guidance starts.",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Declining camera tracking still allows you to complete all manual care plans and journal entries.",
                                style = MaterialTheme.typography.bodySmall,
                                color = OutlineColor
                            )
                        }
                    }
                }
            }

            // Option 2: Caregiver Data Sharing
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
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                                    text = "Caregiver Update Sharing",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = "Share completed task summaries with your authorized CareCircle.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        Switch(
                            checked = caregiverSharingOptIn,
                            onCheckedChange = { caregiverSharingOptIn = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnSagePrimary,
                                checkedTrackColor = SagePrimary
                            )
                        )
                    }

                    Surface(
                        color = WarmStoneBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "In this demo, sharing preferences are saved locally. No external accounts are granted access without your explicit in-app confirmation.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OutlineColor,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            // Save Choices Button
            SmaPrimaryButton(
                text = "Save Preferences & Continue",
                leadingIcon = Icons.Default.Save,
                onClick = {
                    try {
                        store.saveChoices(cameraOptIn, caregiverSharingOptIn)
                        Toast.makeText(
                            context,
                            "Consent preferences saved on this device.",
                            Toast.LENGTH_SHORT
                        ).show()
                        onContinue()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error saving choices: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            SmaSecondaryButton(
                text = "Back",
                onClick = onBack
            )

            Spacer(Modifier.height(36.dp))
        }
    }
}
