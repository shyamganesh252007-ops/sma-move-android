
package com.smamove.android.feature.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smamove.android.ui.components.SmaOutlinedButton
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalLoginScreen(
    onNavigateBack: () -> Unit,
    onNavigateToRegistration: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current

    // Shared persistent demo session storage.
    val sessionStore = AuthSessionStore(context)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var councilJurisdiction by remember {
        mutableStateOf("Delhi Medical Council (DMC)")
    }

    val councilOptions = listOf(
        "Delhi Medical Council (DMC)",
        "Maharashtra Medical Council (MMC)",
        "Tamil Nadu Medical Council (TNMC)",
        "Karnataka Medical Council (KMC)",
        "All India Institute of Physical Medicine & Rehab (AIIPMR)",
        "Indian Association of Physiotherapists (IAP)"
    )

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Practitioner Portal",
                onBack = onNavigateBack
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

            // Header Information
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SagePrimaryFixed
                )
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(SagePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = OnSagePrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Healthcare Professional Portal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnSagePrimaryFixed
                        )

                        Text(
                            text = "Secure access for SMA-Move authorized physical therapists and clinical specialists in India.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSagePrimaryFixedVariant
                        )
                    }
                }
            }

            // Sign-In Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceContainerLow
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Sign In with Clinical Credentials",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text("Professional Hospital Email")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Mail,
                                contentDescription = null,
                                tint = OutlineColor
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = OutlineColor
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Medical / PT Council Jurisdiction (India)",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnWarmStoneVariant
                        )

                        councilOptions.take(3).forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = councilJurisdiction == option,
                                    onClick = {
                                        councilJurisdiction = option
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = SagePrimary
                                    )
                                )

                                Spacer(Modifier.width(6.dp))

                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnWarmStone
                                )
                            }
                        }
                    }

                    SmaPrimaryButton(
                        text = "Authenticate & Sign In",
                        leadingIcon = Icons.AutoMirrored.Filled.Login,
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {

                                Toast.makeText(
                                    context,
                                    "Please enter your clinical credentials.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {

                                // Prototype only:
                                // This does not verify professional credentials.
                                sessionStore.saveSession(
                                    AuthSessionStore.ROLE_PROFESSIONAL
                                )

                                Toast.makeText(
                                    context,
                                    "Professional demo session started.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onLoginSuccess()
                            }
                        }
                    )
                }
            }

            // New Practitioner Registration Link
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "New Specialist Registration",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )

                    Text(
                        text = "Need to register your HPR ID and submit clinical registration documents for ABDM compliance?",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant
                    )

                    SmaOutlinedButton(
                        text = "Register Clinical Identity & HPR ID",
                        leadingIcon = Icons.Default.Badge,
                        onClick = onNavigateToRegistration
                    )
                }
            }

            // Verification Notice
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = SurfaceContainerHigh
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.GppMaybe,
                        contentDescription = null,
                        tint = TerracottaTertiary,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "Verification Notice: Access to patient care plans is intended for verified clinical specialists registered with appropriate state medical boards or ABDM healthcare registry. Demo access does not verify professional credentials.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(Modifier.height(48.dp))
        }
    }
}
