
package com.smamove.android.feature.settings

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

private const val SECURITY_PREFS = "sma_move_security_preferences"
private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSecuritySettingsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val preferences = remember(context) {
        context.getSharedPreferences(
            SECURITY_PREFS,
            Context.MODE_PRIVATE
        )
    }

    var biometricEnabled by remember(context) {
        mutableStateOf(
            preferences.getBoolean(KEY_BIOMETRIC_ENABLED, false)
        )
    }

    var showClearDialog by remember {
        mutableStateOf(false)
    }

    val biometricManager = remember(context) {
        BiometricManager.from(context)
    }

    val biometricAvailability = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG
    )

    val biometricAvailable =
        biometricAvailability == BiometricManager.BIOMETRIC_SUCCESS

    fun authenticateBiometric(enableAfterAuthentication: Boolean) {
        val activity = context as? FragmentActivity

        if (activity == null) {
            Toast.makeText(
                context,
                "Biometric authentication is unavailable in this screen.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (!biometricAvailable) {
            val message = when (biometricAvailability) {
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    "This device does not have supported biometric hardware."

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    "Biometric hardware is currently unavailable."

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    "Please enroll a fingerprint or other strong biometric in device settings."

                else ->
                    "Strong biometric authentication is currently unavailable."
            }

            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val executor = ContextCompat.getMainExecutor(context)

        val prompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    preferences.edit()
                        .putBoolean(
                            KEY_BIOMETRIC_ENABLED,
                            enableAfterAuthentication
                        )
                        .apply()

                    biometricEnabled = enableAfterAuthentication

                    val message = if (enableAfterAuthentication) {
                        "Biometric authentication enabled."
                    } else {
                        "Biometric authentication disabled."
                    }

                    Toast.makeText(
                        context,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)

                    Toast.makeText(
                        context,
                        errString,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                    Toast.makeText(
                        context,
                        "Biometric not recognized. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(
                if (enableAfterAuthentication) {
                    "Enable Biometric Authentication"
                } else {
                    "Disable Biometric Authentication"
                }
            )
            .setSubtitle(
                "Verify your identity to change SMA-Move security settings."
            )
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
            .build()

        prompt.authenticate(promptInfo)
    }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Account Security",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // Introduction

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Device Protection & Passcodes",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )

                Text(
                    text = "Protect access to your SMA-Move health information using your device's security features.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant
                )
            }

            // Biometric and PIN Settings

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceContainerLow
                )
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
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Biometric Authentication",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )

                            Text(
                                text = if (biometricAvailable) {
                                    "Use your enrolled strong biometric to change this setting."
                                } else {
                                    "Strong biometric authentication is not currently available."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }

                        Switch(
                            checked = biometricEnabled,
                            enabled = biometricAvailable,
                            onCheckedChange = { requestedState ->
                                authenticateBiometric(requestedState)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnSagePrimary,
                                checkedTrackColor = SagePrimary
                            )
                        )
                    }

                    HorizontalDivider(
                        color = OutlineVariantColor.copy(alpha = 0.3f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "App Passcode Lock (PIN)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )

                            Text(
                                text = "PIN setup and app-lock enforcement will be implemented in the next security step.",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }

                        Switch(
                            checked = false,
                            onCheckedChange = {},
                            enabled = false
                        )
                    }
                }
            }

            // Security Status

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceContainerLow
                )
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
                        Text(
                            text = "Storage Security Status",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )

                        SmaStatusChip(
                            text = "Private App Storage",
                            icon = Icons.Default.Lock,
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }

                    Text(
                        text = "Android isolates this app's private storage from other ordinary apps. Encryption of individual health records has not yet been verified or implemented.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant
                    )
                }
            }

            // Local Demo Data Cleanup

            Button(
                onClick = {
                    showClearDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ClinicalErrorContainer,
                    contentColor = OnClinicalErrorContainer
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = null,
                        tint = ClinicalError
                    )

                    Text("Clear Local Demo Data & Cache")
                }
            }

            SmaSecondaryButton(
                text = "Back",
                onClick = onBack
            )
        }
    }

    // Cleanup Information Dialog

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = {
                showClearDialog = false
            },
            title = {
                Text("Local Data Cleanup")
            },
            text = {
                Text(
                    "Local demo-data cleanup is not connected yet. No records or preferences have been deleted."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showClearDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showClearDialog = false
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}
