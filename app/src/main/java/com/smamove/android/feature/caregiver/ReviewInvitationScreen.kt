package com.smamove.android.feature.caregiver

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
fun ReviewInvitationScreen(
    onBack: () -> Unit,
    onApproved: () -> Unit
) {
    val context = LocalContext.current
    var permCarePlan by remember { mutableStateOf(true) }
    var permSummaries by remember { mutableStateOf(true) }
    var permReminders by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Review Invitation",
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
            // Status Tag & ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmaStatusChip(
                    text = "Pending Patient Approval",
                    icon = Icons.Default.Schedule,
                    containerColor = TerracottaTertiaryFixed,
                    contentColor = OnTerracottaTertiaryFixed
                )
                Text(
                    text = "ID: #INV-8842",
                    style = MaterialTheme.typography.bodySmall,
                    color = OutlineColor
                )
            }

            // Caregiver Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(SagePrimaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "MV",
                                color = OnSagePrimaryContainer,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {
                            Text(
                                text = "Marcus Vance",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Primary Caregiver / Family Link",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                            Spacer(Modifier.height(4.dp))
                            SmaStatusChip(
                                text = "Verified Mobile: +91 98765 43210",
                                containerColor = OliveSecondaryContainer,
                                contentColor = OnOliveSecondaryContainer
                            )
                        }
                    }

                    Surface(
                        color = WarmStoneBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Access requested on 24 Oct 2026. Only granted permissions will be visible to this caregiver.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OutlineColor,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            // Requested Permissions
            Text(
                text = "Requested Permission Scope",
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
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Daily Care Plan & Task Status", fontWeight = FontWeight.SemiBold, color = OnWarmStone)
                            Text("View completed daily exercises and adherence.", style = MaterialTheme.typography.bodySmall, color = OnWarmStoneVariant)
                        }
                        Switch(
                            checked = permCarePlan,
                            onCheckedChange = { permCarePlan = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = OnSagePrimary, checkedTrackColor = SagePrimary)
                        )
                    }

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Weekly Wellbeing Summaries", fontWeight = FontWeight.SemiBold, color = OnWarmStone)
                            Text("Receive digest emails of stability and comfort trends.", style = MaterialTheme.typography.bodySmall, color = OnWarmStoneVariant)
                        }
                        Switch(
                            checked = permSummaries,
                            onCheckedChange = { permSummaries = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = OnSagePrimary, checkedTrackColor = SagePrimary)
                        )
                    }

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Appointment & Reminders", fontWeight = FontWeight.SemiBold, color = OnWarmStone)
                            Text("Help coordinate upcoming virtual and in-person visits.", style = MaterialTheme.typography.bodySmall, color = OnWarmStoneVariant)
                        }
                        Switch(
                            checked = permReminders,
                            onCheckedChange = { permReminders = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = OnSagePrimary, checkedTrackColor = SagePrimary)
                        )
                    }
                }
            }

            // Actions
            SmaPrimaryButton(
                text = "Approve & Connect Caregiver",
                leadingIcon = Icons.Default.Check,
                onClick = {
                    CaregiverStore.approveCaregiver("1")
                    Toast.makeText(context, "Caregiver Marcus Vance approved.", Toast.LENGTH_SHORT).show()
                    onApproved()
                }
            )

            SmaOutlinedButton(
                text = "Decline Invitation",
                onClick = {
                    Toast.makeText(context, "Invitation declined.", Toast.LENGTH_SHORT).show()
                    onBack()
                }
            )
        }
    }
}

