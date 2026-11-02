
package com.smamove.android.navigation

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Authentication
import com.smamove.android.feature.auth.AuthSessionStore
import com.smamove.android.feature.auth.FamilyLoginScreen
import com.smamove.android.feature.auth.PatientLoginScreen
import com.smamove.android.feature.auth.ProfessionalLoginScreen

// Features
import com.smamove.android.feature.accessibility.AccessibilitySettingsScreen
import com.smamove.android.feature.accessibility.LiveContrastPreviewScreen
import com.smamove.android.feature.appointments.AppointmentDetailsScreen
import com.smamove.android.feature.caregiver.CaregiverDashboardScreen
import com.smamove.android.feature.caregiver.InviteCaregiverScreen
import com.smamove.android.feature.caregiver.ManageConnectedCaregiversScreen
import com.smamove.android.feature.caregiver.ReviewInvitationScreen
import com.smamove.android.feature.caregiver.SharedCareUpdatesScreen
import com.smamove.android.feature.careplan.CarePlanScreen
import com.smamove.android.feature.careplan.CarePlanViewModel
import com.smamove.android.feature.careplan.CareTaskDetailsScreen
import com.smamove.android.feature.careplan.DailyCarePlanDetailsScreen
import com.smamove.android.feature.journal.CheckInConfirmationScreen
import com.smamove.android.feature.journal.JournalViewModel
import com.smamove.android.feature.journal.WellbeingJournalScreen
import com.smamove.android.feature.journal.WellbeingJournalSymptomScreen
import com.smamove.android.feature.movetrack.MoveTrackConsentOptionsScreen
import com.smamove.android.feature.movetrack.MoveTrackConsentScreen
import com.smamove.android.feature.movetrack.MoveTrackDetailedConsentScreen
import com.smamove.android.feature.movetrack.MoveTrackPrivacySettingsScreen
import com.smamove.android.feature.movetrack.MoveTrackSessionInfoScreen
import com.smamove.android.feature.movetrack.MoveTrackSessionSummaryScreen
import com.smamove.android.feature.patient.PatientDashboardScreen
import com.smamove.android.feature.professional.ProfessionalProfileScreen
import com.smamove.android.feature.professional.ProfessionalRegistrationScreen
import com.smamove.android.feature.professional.ProfessionalVerificationPendingScreen
import com.smamove.android.feature.professional.ProfessionalVerificationSubmissionScreen
import com.smamove.android.feature.roleselection.RoleSelectionScreen
import com.smamove.android.feature.roleselection.UserRole
import com.smamove.android.feature.settings.AccountSecuritySettingsScreen
import com.smamove.android.feature.settings.NotificationPreferencesScreen
import com.smamove.android.feature.settings.ProfileSettingsScreen
import com.smamove.android.feature.system.SystemStatesScreen

private const val SECURITY_PREFS = "sma_move_security_preferences"
private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"

private const val ROUTE_FAMILY_LOGIN = "family_login"

/**
 * App-wide biometric gate.
 *
 * If biometric locking is enabled, the protected navigation UI
 * remains hidden until authentication succeeds.
 */
@Composable
private fun AppBiometricGate(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val lifecycleOwner = LocalLifecycleOwner.current

    val preferences = remember {
        context.getSharedPreferences(
            SECURITY_PREFS,
            Context.MODE_PRIVATE
        )
    }

    var biometricEnabled by remember {
        mutableStateOf(
            preferences.getBoolean(
                KEY_BIOMETRIC_ENABLED,
                false
            )
        )
    }

    var isLocked by remember {
        mutableStateOf(biometricEnabled)
    }

    var retryRequest by remember {
        mutableStateOf(0)
    }

    var authenticationMessage by remember {
        mutableStateOf("")
    }

    // Observe changes made in Account Security Settings.
    DisposableEffect(preferences) {
        val listener =
            android.content.SharedPreferences.OnSharedPreferenceChangeListener {
                    sharedPreferences, key ->

                if (key == KEY_BIOMETRIC_ENABLED) {
                    val enabled = sharedPreferences.getBoolean(
                        KEY_BIOMETRIC_ENABLED,
                        false
                    )

                    biometricEnabled = enabled
                    isLocked = enabled
                    authenticationMessage = ""
                }
            }

        preferences.registerOnSharedPreferenceChangeListener(listener)

        onDispose {
            preferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    // Lock again when the app returns from the background.
    DisposableEffect(lifecycleOwner, biometricEnabled) {
        var wentToBackground = false

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    if (biometricEnabled) {
                        wentToBackground = true
                    }
                }

                Lifecycle.Event.ON_START -> {
                    if (biometricEnabled && wentToBackground) {
                        isLocked = true
                        authenticationMessage = ""
                        retryRequest++
                        wentToBackground = false
                    }
                }

                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Request authentication only when the lock or retry request changes.
    LaunchedEffect(
        isLocked,
        retryRequest,
        biometricEnabled
    ) {
        if (!biometricEnabled || !isLocked) {
            return@LaunchedEffect
        }

        if (activity == null) {
            authenticationMessage =
                "Biometric authentication is unavailable."
            return@LaunchedEffect
        }

        val biometricManager = BiometricManager.from(activity)

        val biometricStatus = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )

        if (biometricStatus != BiometricManager.BIOMETRIC_SUCCESS) {
            authenticationMessage =
                "Strong biometric authentication is unavailable. " +
                        "Check your device's biometric settings."
            return@LaunchedEffect
        }

        val executor = ContextCompat.getMainExecutor(activity)

        val prompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    isLocked = false
                    authenticationMessage = ""
                }

                override fun onAuthenticationFailed() {
                    authenticationMessage =
                        "Fingerprint not recognised. Try again."
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    authenticationMessage = errString.toString()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock SMA-Move")
            .setSubtitle(
                "Verify your identity to access your care space"
            )
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
            .setNegativeButtonText("Cancel")
            .build()

        prompt.authenticate(promptInfo)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (!biometricEnabled || !isLocked) {
            content()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SMA-Move",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Your care space is locked",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Authenticate to continue."
                )

                if (authenticationMessage.isNotBlank()) {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = authenticationMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(
                    onClick = {
                        authenticationMessage = ""
                        retryRequest++
                    }
                ) {
                    Text("Retry fingerprint")
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {

    // Global security gate protects the complete navigation graph.
    AppBiometricGate {

        val context = LocalContext.current

        val sessionStore = remember(context) {
            AuthSessionStore(context)
        }

        // Restore the last remembered demo role.
        val startDestination = remember {
            if (!sessionStore.hasActiveSession()) {
                Routes.ROLE_SELECTION
            } else {
                when (sessionStore.getSessionRole()) {
                    AuthSessionStore.ROLE_PATIENT ->
                        Routes.PATIENT_DASHBOARD

                    AuthSessionStore.ROLE_CAREGIVER ->
                        Routes.CAREGIVER_DASHBOARD

                    AuthSessionStore.ROLE_PROFESSIONAL ->
                        Routes.PROFESSIONAL_PROFILE

                    else ->
                        Routes.ROLE_SELECTION
                }
            }
        }

        val navController = rememberNavController()

        // Shared ViewModels for state retention across dashboard,
        // care plan, and journal.
        val carePlanViewModel: CarePlanViewModel = viewModel()
        val journalViewModel: JournalViewModel = viewModel()

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            // ==========================================
            // 0. ROLE SELECTION & ONBOARDING
            // ==========================================

            composable(Routes.ROLE_SELECTION) {
                RoleSelectionScreen(
                    onNavigateToRole = { role ->
                        when (role) {
                            UserRole.PATIENT ->
                                navController.navigate(Routes.PATIENT_LOGIN)

                            UserRole.FAMILY ->
                                navController.navigate(ROUTE_FAMILY_LOGIN)

                            UserRole.PROFESSIONAL ->
                                navController.navigate(Routes.PROFESSIONAL_LOGIN)
                        }
                    }
                )
            }

            // Patient Login
            composable(Routes.PATIENT_LOGIN) {
                PatientLoginScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onLoginSuccess = {
                        navController.navigate(Routes.PATIENT_DASHBOARD) {
                            popUpTo(Routes.ROLE_SELECTION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // Family / Caregiver Login
            composable(ROUTE_FAMILY_LOGIN) {
                FamilyLoginScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onLoginSuccess = {
                        navController.navigate(Routes.CAREGIVER_DASHBOARD) {
                            popUpTo(Routes.ROLE_SELECTION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // ==========================================
            // A. PATIENT EXPERIENCE (SCREENS 1 - 9)
            // ==========================================

            // 1. Patient Dashboard
            composable(Routes.PATIENT_DASHBOARD) {
                PatientDashboardScreen(
                    carePlanViewModel = carePlanViewModel,
                    journalViewModel = journalViewModel,
                    onOpenMoveTrack = {
                        navController.navigate(Routes.MOVETRACK_CONSENT)
                    },
                    onOpenMoveTrackPrivacy = {
                        navController.navigate(Routes.MOVETRACK_PRIVACY_SETTINGS)
                    },
                    onOpenCarePlan = {
                        navController.navigate(Routes.CARE_PLAN)
                    },
                    onOpenTaskDetails = { taskId ->
                        navController.navigate(Routes.careTaskDetails(taskId))
                    },
                    onOpenJournal = {
                        navController.navigate(Routes.WELLBEING_JOURNAL)
                    },
                    onOpenAppointments = {
                        navController.navigate(Routes.APPOINTMENT_DETAILS)
                    },
                    onOpenProfile = {
                        navController.navigate(Routes.PROFILE_SETTINGS)
                    },
                    onOpenAccessibility = {
                        navController.navigate(Routes.ACCESSIBILITY_SETTINGS)
                    },
                    onOpenPrivacy = {
                        navController.navigate(Routes.MOVETRACK_PRIVACY_SETTINGS)
                    }
                )
            }

            // 2. Daily Care Plan
            composable(Routes.CARE_PLAN) {
                CarePlanScreen(
                    viewModel = carePlanViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenTaskDetails = { taskId ->
                        navController.navigate(Routes.careTaskDetails(taskId))
                    },
                    onNavigateHome = {
                        navController.navigate(Routes.PATIENT_DASHBOARD)
                    },
                    onNavigateJournal = {
                        navController.navigate(Routes.WELLBEING_JOURNAL)
                    },
                    onNavigateProfile = {
                        navController.navigate(Routes.PROFILE_SETTINGS)
                    }
                )
            }

            // 3. Daily Care Plan Details
            composable(Routes.DAILY_CARE_PLAN_DETAILS) {
                DailyCarePlanDetailsScreen(
                    viewModel = carePlanViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenTaskDetails = { taskId ->
                        navController.navigate(Routes.careTaskDetails(taskId))
                    }
                )
            }

            // 4. Care Task Details
            composable(
                route = Routes.CARE_TASK_DETAILS,
                arguments = listOf(
                    navArgument("taskId") {
                        type = NavType.IntType
                        defaultValue = 1
                    }
                )
            ) { backStackEntry ->
                val taskId =
                    backStackEntry.arguments?.getInt("taskId") ?: 1

                CareTaskDetailsScreen(
                    taskId = taskId,
                    viewModel = carePlanViewModel,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 5. Wellbeing Journal
            composable(Routes.WELLBEING_JOURNAL) {
                WellbeingJournalScreen(
                    viewModel = journalViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenSymptomNotes = {
                        navController.navigate(Routes.WELLBEING_JOURNAL_SYMPTOM)
                    },
                    onCheckInConfirmed = {
                        navController.navigate(Routes.CHECK_IN_CONFIRMATION)
                    },
                    onNavigateHome = {
                        navController.navigate(Routes.PATIENT_DASHBOARD)
                    },
                    onNavigateCarePlan = {
                        navController.navigate(Routes.CARE_PLAN)
                    },
                    onNavigateProfile = {
                        navController.navigate(Routes.PROFILE_SETTINGS)
                    }
                )
            }

            // 6. Wellbeing Journal with Symptom Notes
            composable(Routes.WELLBEING_JOURNAL_SYMPTOM) {
                WellbeingJournalSymptomScreen(
                    viewModel = journalViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onCheckInConfirmed = {
                        navController.navigate(Routes.CHECK_IN_CONFIRMATION)
                    }
                )
            }

            // 7. Check-in Confirmation
            composable(Routes.CHECK_IN_CONFIRMATION) {
                CheckInConfirmationScreen(
                    viewModel = journalViewModel,
                    onNavigateHome = {
                        navController.navigate(Routes.PATIENT_DASHBOARD) {
                            popUpTo(Routes.PATIENT_DASHBOARD) {
                                inclusive = false
                            }
                        }
                    },
                    onNavigateCarePlan = {
                        navController.navigate(Routes.CARE_PLAN)
                    }
                )
            }

            // 8. Appointment Details
            composable(Routes.APPOINTMENT_DETAILS) {
                AppointmentDetailsScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 9. Profile and Settings
            composable(Routes.PROFILE_SETTINGS) {
                ProfileSettingsScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenAccessibility = {
                        navController.navigate(Routes.ACCESSIBILITY_SETTINGS)
                    },
                    onOpenPrivacy = {
                        navController.navigate(Routes.MOVETRACK_PRIVACY_SETTINGS)
                    },
                    onOpenNotifications = {
                        navController.navigate(Routes.NOTIFICATION_PREFERENCES)
                    },
                    onOpenCaregivers = {
                        navController.navigate(Routes.MANAGE_CONNECTED_CAREGIVERS)
                    },
                    onOpenSecurity = {
                        navController.navigate(Routes.ACCOUNT_SECURITY_SETTINGS)
                    },
                    onSignOut = {
                        sessionStore.clearSession()

                        navController.navigate(Routes.ROLE_SELECTION) {
                            popUpTo(Routes.ROLE_SELECTION) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateHome = {
                        navController.navigate(Routes.PATIENT_DASHBOARD)
                    },
                    onNavigateCarePlan = {
                        navController.navigate(Routes.CARE_PLAN)
                    },
                    onNavigateJournal = {
                        navController.navigate(Routes.WELLBEING_JOURNAL)
                    }
                )
            }

            // ==========================================
            // B. MOVETRACK & PRIVACY (SCREENS 10 - 14)
            // ==========================================

            // 10. MoveTrack Introduction
            composable(Routes.MOVETRACK_CONSENT) {
                MoveTrackConsentScreen(
                    onContinue = {
                        navController.navigate(Routes.MOVETRACK_DETAILED_CONSENT)
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 11. Detailed Informed Consent
            composable(Routes.MOVETRACK_DETAILED_CONSENT) {
                MoveTrackDetailedConsentScreen(
                    onContinueToPreferences = {
                        navController.navigate(Routes.MOVETRACK_CONSENT_OPTIONS)
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Session Info
            composable(Routes.MOVETRACK_SESSION_INFO) {
                MoveTrackSessionInfoScreen(
                    onContinue = {
                        navController.navigate(Routes.MOVETRACK_CONSENT_OPTIONS)
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 12. Consent Preferences
            composable(Routes.MOVETRACK_CONSENT_OPTIONS) {
                MoveTrackConsentOptionsScreen(
                    onContinue = {
                        navController.navigate(Routes.MOVETRACK_SESSION_SUMMARY)
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 13. Privacy Centre
            composable(Routes.MOVETRACK_PRIVACY_SETTINGS) {
                MoveTrackPrivacySettingsScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 14. MoveTrack Session Summary
            composable(Routes.MOVETRACK_SESSION_SUMMARY) {
                MoveTrackSessionSummaryScreen(
                    onNavigateHome = {
                        navController.navigate(Routes.PATIENT_DASHBOARD) {
                            popUpTo(Routes.PATIENT_DASHBOARD) {
                                inclusive = false
                            }
                        }
                    }
                )
            }

            // ==========================================
            // C. FAMILY AND CAREGIVER (SCREENS 15 - 19)
            // ==========================================

            // 15. Invite Caregiver
            composable(Routes.INVITE_CAREGIVER) {
                InviteCaregiverScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onInvitationSent = {
                        navController.navigate(Routes.MANAGE_CONNECTED_CAREGIVERS)
                    }
                )
            }

            // 16. Review Caregiver Invitation
            composable(Routes.REVIEW_INVITATION) {
                ReviewInvitationScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onApproved = {
                        navController.navigate(Routes.MANAGE_CONNECTED_CAREGIVERS)
                    }
                )
            }

            // 17. Caregiver Dashboard
            composable(Routes.CAREGIVER_DASHBOARD) {
                CaregiverDashboardScreen(
                    carePlanViewModel = carePlanViewModel,
                    onOpenSharedUpdates = {
                        navController.navigate(Routes.SHARED_CARE_UPDATES)
                    },
                    onOpenManageCaregivers = {
                        navController.navigate(Routes.MANAGE_CONNECTED_CAREGIVERS)
                    },
                    onOpenTaskDetails = { taskId ->
                        navController.navigate(Routes.careTaskDetails(taskId))
                    },
                    onSignOut = {
                        sessionStore.clearSession()

                        navController.navigate(Routes.ROLE_SELECTION) {
                            popUpTo(Routes.ROLE_SELECTION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // 18. Shared Care Updates
            composable(Routes.SHARED_CARE_UPDATES) {
                SharedCareUpdatesScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 19. Manage Connected Caregivers
            composable(Routes.MANAGE_CONNECTED_CAREGIVERS) {
                ManageConnectedCaregiversScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenInvite = {
                        navController.navigate(Routes.INVITE_CAREGIVER)
                    },
                    onOpenReview = {
                        navController.navigate(Routes.REVIEW_INVITATION)
                    }
                )
            }

            // ==========================================
            // D. HEALTHCARE PROFESSIONAL (SCREENS 20 - 24)
            // ==========================================

            // 20. Healthcare Professional Sign-In
            composable(Routes.PROFESSIONAL_LOGIN) {
                ProfessionalLoginScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToRegistration = {
                        navController.navigate(Routes.PROFESSIONAL_REGISTRATION)
                    },
                    onLoginSuccess = {
                        navController.navigate(Routes.PROFESSIONAL_PROFILE) {
                            popUpTo(Routes.ROLE_SELECTION) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // 21. Professional Registration and Identity Details
            composable(Routes.PROFESSIONAL_REGISTRATION) {
                ProfessionalRegistrationScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onProceedToDocuments = {
                        navController.navigate(Routes.PROFESSIONAL_VERIFICATION_SUBMISSION)
                    }
                )
            }

            // 22. Verification Submission
            composable(Routes.PROFESSIONAL_VERIFICATION_SUBMISSION) {
                ProfessionalVerificationSubmissionScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onSubmitSuccess = {
                        navController.navigate(Routes.PROFESSIONAL_VERIFICATION_PENDING)
                    }
                )
            }

            // 23. Verification Pending
            composable(Routes.PROFESSIONAL_VERIFICATION_PENDING) {
                ProfessionalVerificationPendingScreen(
                    onExploreWorkspace = {
                        navController.navigate(Routes.PROFESSIONAL_PROFILE)
                    },
                    onBackToLogin = {
                        navController.navigate(Routes.PROFESSIONAL_LOGIN) {
                            popUpTo(Routes.PROFESSIONAL_LOGIN) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // 24. Professional Profile & Authorized Access Overview
            composable(Routes.PROFESSIONAL_PROFILE) {
                ProfessionalProfileScreen(
                    onSignOut = {
                        sessionStore.clearSession()

                        navController.navigate(Routes.ROLE_SELECTION) {
                            popUpTo(Routes.ROLE_SELECTION) {
                                inclusive = true
                            }
                        }
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ==========================================
            // E. ACCESSIBILITY & SYSTEM SCREENS (25 - 29)
            // ==========================================

            // 25. Accessibility Settings
            composable(Routes.ACCESSIBILITY_SETTINGS) {
                AccessibilitySettingsScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenLivePreview = {
                        navController.navigate(Routes.LIVE_CONTRAST_PREVIEW)
                    }
                )
            }

            // 26. Text-Size & Contrast Preview
            composable(Routes.LIVE_CONTRAST_PREVIEW) {
                LiveContrastPreviewScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 27. Notification Preferences
            composable(Routes.NOTIFICATION_PREFERENCES) {
                NotificationPreferencesScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 28. Account Security Settings
            composable(Routes.ACCOUNT_SECURITY_SETTINGS) {
                AccountSecuritySettingsScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 29. System States & Resilience
            composable(Routes.SYSTEM_STATES) {
                SystemStatesScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
