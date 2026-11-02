
package com.smamove.android.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.smamove.android.ui.theme.SMAAndroidTheme

@Composable
fun PatientLoginScreen(
    onNavigateBack: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var demoLoginSuccessful by rememberSaveable {
        mutableStateOf(false)
    }

    PatientLoginContent(
        email = email,
        password = password,
        errorMessage = errorMessage,
        demoLoginSuccessful = demoLoginSuccessful,

        onEmailChange = {
            email = it
            errorMessage = null
        },

        onPasswordChange = {
            password = it
            errorMessage = null
        },

        onSignIn = {
            if (email.isBlank() || password.isBlank()) {
                errorMessage =
                    "Enter any email and password to continue."
            } else {
                // Demo-only login. No backend authentication.
                demoLoginSuccessful = true
                errorMessage = null
            }
        },

        onNavigateBack = onNavigateBack,

        onLoginSuccess = {
            // Remember the patient demo session.
            AuthSessionStore(context).saveSession(
                AuthSessionStore.ROLE_PATIENT
            )

            onLoginSuccess()
        }
    )
}

@Composable
private fun PatientLoginContent(
    email: String,
    password: String,
    errorMessage: String?,
    demoLoginSuccessful: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn: () -> Unit,
    onNavigateBack: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Patient Sign In",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "SMA-Move prototype · Demo Mode",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(24.dp))

            if (demoLoginSuccessful) {

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "✓ Demo Login Successful",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = "Demo access only. No account was authenticated, " +
                                    "no password was saved, and no real patient records " +
                                    "were accessed.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = onLoginSuccess,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Continue to Demo Dashboard")
                        }

                        TextButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Back to Roles")
                        }
                    }
                }

            } else {

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    placeholder = { Text("Enter any email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    placeholder = { Text("Enter any password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Demo mode: any non-empty email and password will work.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                if (errorMessage != null) {
                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = onSignIn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp)
                ) {
                    Text("Sign In")
                }

                Spacer(Modifier.height(12.dp))

                TextButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Roles")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Patient Login")
@Composable
private fun PatientLoginPreview() {
    SMAAndroidTheme {
        PatientLoginContent(
            email = "",
            password = "",
            errorMessage = null,
            demoLoginSuccessful = false,
            onEmailChange = {},
            onPasswordChange = {},
            onSignIn = {},
            onNavigateBack = {},
            onLoginSuccess = {}
        )
    }
}

@Preview(showBackground = true, name = "Patient Login Success")
@Composable
private fun PatientLoginSuccessPreview() {
    SMAAndroidTheme {
        PatientLoginContent(
            email = "",
            password = "",
            errorMessage = null,
            demoLoginSuccessful = true,
            onEmailChange = {},
            onPasswordChange = {},
            onSignIn = {},
            onNavigateBack = {},
            onLoginSuccess = {}
        )
    }
}
