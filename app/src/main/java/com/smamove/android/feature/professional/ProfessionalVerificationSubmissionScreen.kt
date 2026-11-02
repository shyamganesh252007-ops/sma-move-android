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
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalVerificationSubmissionScreen(
    onBack: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Document Verification",
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(SagePrimaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = OnSagePrimaryContainer
                        )
                    }

                    Column {
                        Text(
                            text = "Credential Document Checklist",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "Upload required proof of registration to complete your ABDM/HPR clinical verification package.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnWarmStoneVariant
                        )
                    }
                }
            }

            // Document 1
            DocumentPlaceholderCard(
                title = "State Medical / PT Council Certificate",
                subtitle = "Active registration certificate issued by DMC/MMC/State Council",
                fileName = "Medical_Council_Reg_Certificate_2024.pdf",
                fileSize = "2.4 MB • Ready for submission",
                status = "Attached",
                statusColor = OliveSecondaryContainer,
                statusTextColor = OnOliveSecondaryContainer
            )

            // Document 2
            DocumentPlaceholderCard(
                title = "Government Photo Identity",
                subtitle = "Aadhaar Card, Passport, or PAN for identity matching",
                fileName = "Govt_Photo_Identity_Card.pdf",
                fileSize = "1.8 MB • Ready for submission",
                status = "Attached",
                statusColor = OliveSecondaryContainer,
                statusTextColor = OnOliveSecondaryContainer
            )

            // Document 3
            DocumentPlaceholderCard(
                title = "Hospital / Institution Employment Badge",
                subtitle = "Official hospital ID showing clinical appointment",
                fileName = "AIIMS_Specialist_Staff_ID.jpg",
                fileSize = "980 KB • Ready for submission",
                status = "Attached",
                statusColor = OliveSecondaryContainer,
                statusTextColor = OnOliveSecondaryContainer
            )

            // Submit Button
            SmaPrimaryButton(
                text = "Submit Verification Package",
                leadingIcon = Icons.Default.CloudUpload,
                onClick = {
                    Toast.makeText(context, "Verification package submitted for review (Demo).", Toast.LENGTH_SHORT).show()
                    onSubmitSuccess()
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
private fun DocumentPlaceholderCard(
    title: String,
    subtitle: String,
    fileName: String,
    fileSize: String,
    status: String,
    statusColor: androidx.compose.ui.graphics.Color,
    statusTextColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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

                SmaStatusChip(
                    text = status,
                    containerColor = statusColor,
                    contentColor = statusTextColor
                )
            }

            Surface(
                color = WarmStoneBackground,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = SagePrimary
                    )
                    Column {
                        Text(
                            text = fileName,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = OnWarmStone
                        )
                        Text(
                            text = fileSize,
                            style = MaterialTheme.typography.bodySmall,
                            color = OutlineColor
                        )
                    }
                }
            }
        }
    }
}

