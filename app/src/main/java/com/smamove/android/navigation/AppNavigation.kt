
package com.smamove.android.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smamove.android.feature.auth.PatientLoginScreen
import com.smamove.android.feature.careplan.CarePlanScreen
import com.smamove.android.feature.careplan.CarePlanViewModel
import com.smamove.android.feature.journal.JournalViewModel
import com.smamove.android.feature.journal.WellbeingJournalScreen
import com.smamove.android.feature.movetrack.MoveTrackConsentScreen
import com.smamove.android.feature.patient.PatientDashboardScreen
import com.smamove.android.feature.roleselection.RoleSelectionScreen
import com.smamove.android.feature.roleselection.UserRole

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val carePlanViewModel: CarePlanViewModel = viewModel()
    val journalViewModel: JournalViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.ROLE_SELECTION
    ) {
        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(
                onNavigateToRole = { role ->
                    when (role) {
                        UserRole.PATIENT ->
                            navController.navigate(Routes.PATIENT_LOGIN)

                        UserRole.FAMILY ->
                            navController.navigate(Routes.FAMILY_LOGIN)

                        UserRole.PROFESSIONAL ->
                            navController.navigate(Routes.PROFESSIONAL_LOGIN)
                    }
                }
            )
        }

        composable(Routes.PATIENT_LOGIN) {
            PatientLoginScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLoginSuccess = {
                    navController.navigate(Routes.PATIENT_DASHBOARD) {
                        popUpTo(Routes.ROLE_SELECTION)
                    }
                }
            )
        }

        composable(Routes.FAMILY_LOGIN) {
            PlaceholderScreen(
                title = "Family & Caregiver",
                message = "Caregiver access is not implemented yet.",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PROFESSIONAL_LOGIN) {
            PlaceholderScreen(
                title = "Healthcare Professional",
                message = "Professional verification is not implemented yet.",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PATIENT_DASHBOARD) {
            PatientDashboardScreen(
                carePlanViewModel = carePlanViewModel,
                onOpenMoveTrack = {
                    navController.navigate(Routes.MOVETRACK_CONSENT)
                },
                onOpenCarePlan = {
                    navController.navigate(Routes.CARE_PLAN)
                },
                onOpenJournal = {
                    navController.navigate(Routes.WELLBEING_JOURNAL)
                }
            )
        }

        composable(Routes.CARE_PLAN) {
            CarePlanScreen(
                viewModel = carePlanViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.WELLBEING_JOURNAL) {
            WellbeingJournalScreen(
                viewModel = journalViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.MOVETRACK_CONSENT) {
            MoveTrackConsentScreen(
                onContinue = {
                    // Next: session information and explicit consent.
                    // Do not activate the camera yet.
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    message: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(title)
        Text(
            text = message,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
