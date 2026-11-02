package com.smamove.android.feature.professional

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
fun ProfessionalRegistrationScreen(
    onBack: () -> Unit,
    onProceedToDocuments: () -> Unit
) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("Dr. Eleanor Vance") }
    var credentialId by remember { mutableStateOf("DMC-PT-2024-8942") }
    var hprId by remember { mutableStateOf("1234-5678-9012-3456") }
    var hospital by remember { mutableStateOf("AIIMS / Neuromuscular Specialty Unit") }
    var selectedSpecialty by remember { mutableStateOf("Physical Therapy & Rehabilitation") }

    val specialties = listOf(
        "Physical Therapy & Rehabilitation",
        "Pediatric & Adult Pulmonology",
        "Neuromuscular Medicine",
        "Clinical Neurology & Assistive Tech"
    )

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Clinical Registration",
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
            // Header Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SagePrimaryFixed)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(SagePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = null,
                            tint = OnSagePrimary
                        )
                    }

                    Column {
                        Text(
                            text = "Specialist Verification Portal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnSagePrimaryFixed
                        )
                        Text(
                            text = "Register your clinical credentials to access authorized patient care plans and telemetry.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSagePrimaryFixedVariant
                        )
                    }
                }
            }

            // Identity Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Legal Identity & Clinical Practice",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Legal Name (as per medical council)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = credentialId,
                        onValueChange = { credentialId = it },
                        label = { Text("State Medical / PT Council Registration No.") },
                        placeholder = { Text("e.g. DMC-PT-2024-8942") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = hprId,
                        onValueChange = { hprId = it },
                        label = { Text("National Healthcare Professional Registry (HPR ID)") },
                        placeholder = { Text("XXXX-XXXX-XXXX-XXXX") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = hospital,
                        onValueChange = { hospital = it },
                        label = { Text("Hospital or Clinical Affiliation") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Primary Specialty",
                            style = MaterialTheme.typography.labelMedium,
                            color = OnWarmStoneVariant
                        )
                        specialties.forEach { spec ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedSpecialty == spec,
                                    onClick = { selectedSpecialty = spec },
                                    colors = RadioButtonDefaults.colors(selectedColor = SagePrimary)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(spec, style = MaterialTheme.typography.bodyMedium, color = OnWarmStone)
                            }
                        }
                    }
                }
            }

            // Action
            SmaPrimaryButton(
                text = "Continue to Document Submission",
                leadingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = {
                    if (fullName.isBlank() || credentialId.isBlank()) {
                        Toast.makeText(context, "Please fill in all mandatory identity fields.", Toast.LENGTH_SHORT).show()
                    } else {
                        onProceedToDocuments()
                    }
                }
            )

            SmaSecondaryButton(
                text = "Back",
                onClick = onBack
            )

            Spacer(Modifier.height(48.dp))
        }
    }
}

