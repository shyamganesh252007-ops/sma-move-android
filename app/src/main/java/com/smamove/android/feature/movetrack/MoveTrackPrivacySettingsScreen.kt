
package com.smamove.android.feature.movetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import java.text.DateFormat
import java.util.Date

private val PrivacyBg = Color(0xFFF5F8FC)
private val PrivacyInk = Color(0xFF10243A)
private val PrivacyMuted = Color(0xFF586B7B)
private val PrivacyTeal = Color(0xFF087F76)
private val PrivacyTealDark = Color(0xFF06665F)
private val PrivacySoft = Color(0xFFE5F4F1)

@Composable
fun MoveTrackPrivacySettingsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val store = remember(context) {
        MoveTrackConsentStore(context)
    }

    var record by remember {
        mutableStateOf<MoveTrackConsentRecord?>(null)
    }

    var showClearConfirmation by remember {
        mutableStateOf(false)
    }

    var statusMessage by remember {
        mutableStateOf<String?>(null)
    }

    fun refreshRecord() {
        record = store.getChoices()
    }

    LaunchedEffect(store) {
        refreshRecord()
    }

    if (showClearConfirmation) {
        AlertDialog(
            onDismissRequest = {
                showClearConfirmation = false
            },
            title = {
                Text("Clear saved preferences?")
            },
            text = {
                Text(
                    "This removes the local MoveTrack consent " +
                            "record from this device. It does not delete " +
                            "other SMA-Move records."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        store.clearChoices()
                        refreshRecord()
                        statusMessage = "Saved consent record cleared."
                        showClearConfirmation = false
                    }
                ) {
                    Text(
                        text = "Clear record",
                        color = Color(0xFFB3261E)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showClearConfirmation = false
                    }
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color.White
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrivacyBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "SMA-MOVE  /  PRIVACY",
            color = PrivacyTealDark,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(
                text = "Your privacy.\nYour control.",
                color = PrivacyInk,
                fontSize = 32.sp,
                lineHeight = 39.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Review your saved MoveTrack preferences " +
                        "or withdraw them at any time.",
                color = PrivacyMuted,
                fontSize = 16.sp,
                lineHeight = 25.sp
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = PrivacySoft
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "LOCAL CONSENT RECORD",
                    color = PrivacyTealDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.1.sp
                )

                Text(
                    text = if (record == null) {
                        "No saved consent record"
                    } else {
                        "Saved on this device"
                    },
                    color = PrivacyInk,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = record?.let {
                        "Consent version: ${it.consentVersion}\n" +
                                "Last updated: ${
                                    DateFormat.getDateTimeInstance().format(
                                        Date(it.updatedAtMillis)
                                    )
                                }"
                    } ?: "Your preferences have not been saved yet.",
                    color = PrivacyMuted,
                    fontSize = 13.sp,
                    lineHeight = 21.sp
                )
            }
        }

        PrivacyPreferenceCard(
            title = "Camera-based movement analysis",
            description = "Your saved preference for the planned " +
                    "on-device movement feature.",
            enabled = record?.cameraOptIn == true,
            enabledLabel = "Opted in",
            disabledLabel = "Not opted in",
            buttonLabel = "Withdraw camera preference",
            onAction = {
                store.withdrawCameraConsent()
                refreshRecord()
                statusMessage = "Camera preference withdrawn."
            }
        )

        PrivacyPreferenceCard(
            title = "Caregiver sharing",
            description = "Your saved preference to explore optional " +
                    "caregiver sharing.",
            enabled = record?.caregiverSharingOptIn == true,
            enabledLabel = "Preference enabled",
            disabledLabel = "Preference disabled",
            buttonLabel = "Withdraw sharing preference",
            onAction = {
                store.withdrawCaregiverSharing()
                refreshRecord()
                statusMessage = "Caregiver-sharing preference withdrawn."
            }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Text(
                    text = "What these settings mean",
                    color = PrivacyInk,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "These controls update locally saved preferences. " +
                            "They do not grant or revoke Android camera permission, " +
                            "stop an active camera session, or change a cloud " +
                            "sharing configuration. Those features are not " +
                            "connected in this prototype.",
                    color = PrivacyMuted,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            }
        }

        if (statusMessage != null) {
            Surface(
                color = PrivacySoft,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = statusMessage!!,
                    modifier = Modifier.padding(12.dp),
                    color = PrivacyTealDark,
                    fontSize = 13.sp
                )
            }
        }

        OutlinedButton(
            onClick = {
                showClearConfirmation = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = record != null
        ) {
            Text(
                text = "Clear saved consent record",
                color = PrivacyInk,
                fontSize = 14.sp
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(17.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrivacyTealDark,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Back",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = "Clearing this record only removes these local " +
                    "preferences. It does not erase other app data.",
            color = PrivacyMuted,
            fontSize = 12.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun PrivacyPreferenceCard(
    title: String,
    description: String,
    enabled: Boolean,
    enabledLabel: String,
    disabledLabel: String,
    buttonLabel: String,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = if (enabled) {
                                PrivacyTeal
                            } else {
                                Color(0xFF8996A2)
                            },
                            shape = CircleShape
                        )
                )

                Text(
                    text = if (enabled) enabledLabel else disabledLabel,
                    color = if (enabled) {
                        PrivacyTealDark
                    } else {
                        PrivacyMuted
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = title,
                color = PrivacyInk,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = description,
                color = PrivacyMuted,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            OutlinedButton(
                onClick = onAction,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = buttonLabel,
                    fontSize = 13.sp
                )
            }
        }
    }
}
