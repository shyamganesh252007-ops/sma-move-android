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
import androidx.compose.material.icons.automirrored.filled.Send
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
fun InviteCaregiverScreen(
    onBack: () -> Unit,
    onInvitationSent: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("Primary Family Member / Caregiver") }
    var canViewCarePlan by remember { mutableStateOf(true) }
    var canReceiveSummaries by remember { mutableStateOf(true) }

    val relationOptions = listOf(
        "Primary Family Member / Caregiver",
        "Spouse / Partner",
        "Parent / Guardian",
        "Adult Child",
        "Community Support Worker",
        "Physiotherapist / Clinician"
    )

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Invite Caregiver",
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
            // Reassuring Info Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(SagePrimaryFixed, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = OnSagePrimaryFixed
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Secure Care Collaboration",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "Grant trusted family members or support workers visibility into your daily care schedule. You retain full authority to modify or revoke permissions at any time.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnWarmStoneVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Input: Name
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Caregiver Full Name",
                    style = MaterialTheme.typography.labelMedium,
                    color = OnWarmStoneVariant
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("e.g. Marcus Vance or Rahul Sharma") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = OutlineColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
            }

            // Input: Contact
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Phone Number (+91) or Email Address",
                    style = MaterialTheme.typography.labelMedium,
                    color = OnWarmStoneVariant
                )
                OutlinedTextField(
                    value = contact,
                    onValueChange = { contact = it },
                    placeholder = { Text("+91 98765 43210 or care@example.com") },
                    leadingIcon = { Icon(Icons.Default.ContactPhone, contentDescription = null, tint = OutlineColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
            }

            // Relationship Picker
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Relationship",
                    style = MaterialTheme.typography.labelMedium,
                    color = OnWarmStoneVariant
                )
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        relationOptions.forEach { opt ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = relation == opt,
                                    onClick = { relation = opt },
                                    colors = RadioButtonDefaults.colors(selectedColor = SagePrimary)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = opt,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnWarmStone
                                )
                            }
                        }
                    }
                }
            }

            // Permissions Card
            Text(
                text = "Permitted Access Scope",
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
                            Text(
                                text = "View Daily Care Plan & Task Status",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Caregiver can observe routine completion and timings.",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }
                        Checkbox(
                            checked = canViewCarePlan,
                            onCheckedChange = { canViewCarePlan = it },
                            colors = CheckboxDefaults.colors(checkedColor = SagePrimary)
                        )
                    }

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Receive Weekly Wellbeing Summaries",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Digest highlighting fatigue trends and recorded comfort notes.",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }
                        Checkbox(
                            checked = canReceiveSummaries,
                            onCheckedChange = { canReceiveSummaries = it },
                            colors = CheckboxDefaults.colors(checkedColor = SagePrimary)
                        )
                    }
                }
            }

            // Action
            SmaPrimaryButton(
                text = "Send Caregiver Invitation",
                leadingIcon = Icons.AutoMirrored.Filled.Send,
                onClick = {
                    if (name.isBlank() || contact.isBlank()) {
                        Toast.makeText(context, "Please enter caregiver name and contact details.", Toast.LENGTH_SHORT).show()
                    } else {
                        CaregiverStore.addCaregiver(
                            name = name,
                            contact = contact,
                            relation = relation,
                            canViewCarePlan = canViewCarePlan,
                            canReceiveSummaries = canReceiveSummaries
                        )
                        Toast.makeText(context, "Invitation generated for $name (Demo).", Toast.LENGTH_SHORT).show()
                        onInvitationSent()
                    }
                }
            )

            SmaSecondaryButton(
                text = "Cancel",
                onClick = onBack
            )

            Spacer(Modifier.height(48.dp))
        }
    }
}

