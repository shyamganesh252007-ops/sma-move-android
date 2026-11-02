package com.smamove.android.feature.professional

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaOutlinedButton
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalProfileScreen(
    onSignOut: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Practitioner Workspace",
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
            // Clinician Profile Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(SagePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AT",
                            color = OnSagePrimary,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Dr. Aris Thorne, MD",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "Consultant Pulmonology & Neuromuscular Specialist",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnWarmStoneVariant
                        )
                        Text(
                            text = "HPR ID: 9841-2041-8842-1942 • AIIMS New Delhi",
                            style = MaterialTheme.typography.bodySmall,
                            color = OutlineColor
                        )
                        Spacer(Modifier.height(6.dp))
                        SmaStatusChip(
                            text = "Verified ABDM Clinical Specialist",
                            icon = Icons.Default.Verified,
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }
                }
            }

            // Assigned Patients Roster
            Text(
                text = "Assigned Patient Roster (2 Active)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            // Patient 1
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(SagePrimaryFixed, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("EV", fontWeight = FontWeight.Bold, color = OnSagePrimaryFixed)
                            }
                            Column {
                                Text(
                                    text = "Elena Vance",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = "SMA Type 2 • Active Plan #4",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        SmaStatusChip(
                            text = "85% Adherence",
                            containerColor = SagePrimaryContainer,
                            contentColor = OnSagePrimaryContainer
                        )
                    }

                    Surface(
                        color = WarmStoneBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Next Virtual Visit: 14 Nov 2026, 10:00 AM IST",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStone
                            )
                            Icon(Icons.Default.Videocam, contentDescription = null, tint = SagePrimary)
                        }
                    }
                }
            }

            // Patient 2
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(OliveSecondaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("AP", fontWeight = FontWeight.Bold, color = OnOliveSecondaryContainer)
                            }
                            Column {
                                Text(
                                    text = "Aarav Patel",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmStone
                                )
                                Text(
                                    text = "SMA Type 3 • Active Plan #2",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStoneVariant
                                )
                            }
                        }

                        SmaStatusChip(
                            text = "92% Adherence",
                            containerColor = SagePrimaryContainer,
                            contentColor = OnSagePrimaryContainer
                        )
                    }

                    Surface(
                        color = WarmStoneBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Next Clinic Follow-up: 28 Nov 2026",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStone
                            )
                            Icon(Icons.Default.LocalHospital, contentDescription = null, tint = SagePrimary)
                        }
                    }
                }
            }

            // Clinical Authority Scope
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Authorized Clinical Scope",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    Text(
                        text = "• Prescription and calibration of cough assist machine pressures\n• Observation of patient daily reflection trends and fatigue scores\n• Review of on-device skeletal coordinate stability metrics (MoveTrack)\n• Verification of designated family caregivers",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant,
                        lineHeight = 20.sp
                    )
                }
            }

            // Actions
            SmaSecondaryButton(
                text = "Switch Role or Sign Out",
                leadingIcon = Icons.Default.Logout,
                onClick = onSignOut
            )

            Spacer(Modifier.height(48.dp))
        }
    }
}

