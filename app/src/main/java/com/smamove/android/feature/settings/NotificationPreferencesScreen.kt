package com.smamove.android.feature.settings

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
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPreferencesScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var morningTaskAlert by remember { mutableStateOf(true) }
    var afternoonMobilityAlert by remember { mutableStateOf(true) }
    var eveningReflectionAlert by remember { mutableStateOf(true) }
    var appointmentAlert by remember { mutableStateOf(true) }
    var smsAlertsEnabled by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Notification Preferences",
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
                    text = "Care Plan & Check-in Reminders",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Choose how and when SMA-Move gently reminds you of scheduled respiratory exercises, positioning, and reflections.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant
                )
            }

            // Routine Alerts Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NotificationToggleRow(
                        title = "Morning Respiratory Protocol (09:00 AM)",
                        subtitle = "Alert for BiPAP & cough assist routine",
                        checked = morningTaskAlert,
                        onCheckedChange = { morningTaskAlert = it }
                    )

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    NotificationToggleRow(
                        title = "Afternoon Mobility & Stretch (02:00 PM)",
                        subtitle = "Gentle reminder to shift position and stretch limbs",
                        checked = afternoonMobilityAlert,
                        onCheckedChange = { afternoonMobilityAlert = it }
                    )

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    NotificationToggleRow(
                        title = "Evening Wellbeing Reflection (08:30 PM)",
                        subtitle = "Daily prompt to log comfort, fatigue, and posture notes",
                        checked = eveningReflectionAlert,
                        onCheckedChange = { eveningReflectionAlert = it }
                    )

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    NotificationToggleRow(
                        title = "Clinical Appointment Notices",
                        subtitle = "Alert 1 hour before scheduled telehealth consultations",
                        checked = appointmentAlert,
                        onCheckedChange = { appointmentAlert = it }
                    )
                }
            }

            // SMS Channel
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NotificationToggleRow(
                        title = "SMS Backup Reminders (+91 Phone)",
                        subtitle = "Receive critical visit links via SMS (Standard Indian carrier rates apply)",
                        checked = smsAlertsEnabled,
                        onCheckedChange = { smsAlertsEnabled = it }
                    )
                }
            }

            SmaPrimaryButton(
                text = "Save Notification Settings",
                leadingIcon = Icons.Default.Check,
                onClick = {
                    Toast.makeText(context, "Notification preferences updated.", Toast.LENGTH_SHORT).show()
                    onBack()
                }
            )

            SmaSecondaryButton(
                text = "Back",
                onClick = onBack
            )
        }
    }
}

@Composable
private fun NotificationToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = OnWarmStoneVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = OnSagePrimary,
                checkedTrackColor = SagePrimary
            )
        )
    }
}

