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
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageConnectedCaregiversScreen(
    onBack: () -> Unit,
    onOpenInvite: () -> Unit,
    onOpenReview: () -> Unit
) {
    val context = LocalContext.current
    val caregivers = CaregiverStore.caregivers

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Connected CareCircle",
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
                    text = "Authorized CareCircle",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Manage family members, caregivers, and community healthcare workers authorized to view your care plan.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant
                )
            }

            // Pending Invitation Review Banner (if any)
            val pending = caregivers.firstOrNull { it.status.contains("Pending") }
            if (pending != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = TerracottaTertiaryFixed)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PendingActions,
                                contentDescription = null,
                                tint = OnTerracottaTertiaryFixed
                            )
                            Text(
                                text = "Action Required: Pending Invitation",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnTerracottaTertiaryFixed
                            )
                        }

                        Text(
                            text = "${pending.name} has requested access to view daily care plans.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnTerracottaTertiaryFixedVariant
                        )

                        Button(
                            onClick = onOpenReview,
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaTertiary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Review Permissions")
                        }
                    }
                }
            }

            // Connected Caregivers List
            Text(
                text = "Connected Members (${caregivers.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            caregivers.forEach { member ->
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
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(OliveSecondaryContainer, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = OnOliveSecondaryContainer
                                    )
                                }

                                Column {
                                    Text(
                                        text = member.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = OnWarmStone
                                    )
                                    Text(
                                        text = member.relation,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = OnWarmStoneVariant
                                    )
                                    Text(
                                        text = member.contact,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = OutlineColor
                                    )
                                }
                            }

                            SmaStatusChip(
                                text = member.status,
                                containerColor = if (member.status == "Active") SagePrimaryFixed else SurfaceContainerHigh,
                                contentColor = if (member.status == "Active") OnSagePrimaryFixed else OnWarmStoneVariant
                            )
                        }

                        // Permission chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (member.canViewCarePlan) {
                                SmaStatusChip(
                                    text = "Care Plan",
                                    icon = Icons.Default.Check,
                                    containerColor = SurfaceContainerHigh,
                                    contentColor = OnWarmStoneVariant
                                )
                            }
                            if (member.canReceiveSummaries) {
                                SmaStatusChip(
                                    text = "Weekly Digest",
                                    icon = Icons.Default.Check,
                                    containerColor = SurfaceContainerHigh,
                                    contentColor = OnWarmStoneVariant
                                )
                            }
                        }

                        HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Connected since ${member.addedDate}",
                                style = MaterialTheme.typography.labelSmall,
                                color = OutlineColor
                            )

                            TextButton(
                                onClick = {
                                    CaregiverStore.removeCaregiver(member.id)
                                    Toast.makeText(context, "${member.name} removed from CareCircle.", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Text("Revoke Access", color = ClinicalError, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // Invite New Member Button
            SmaPrimaryButton(
                text = "Invite New Caregiver",
                leadingIcon = Icons.Default.PersonAdd,
                onClick = onOpenInvite
            )
        }
    }
}

