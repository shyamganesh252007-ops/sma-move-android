package com.smamove.android.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaBottomNavigationBar
import com.smamove.android.ui.components.SmaNavDestination
import com.smamove.android.ui.components.SmaOutlinedButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    onBack: () -> Unit,
    onOpenAccessibility: () -> Unit,
    onOpenPrivacy: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenCaregivers: () -> Unit,
    onOpenSecurity: () -> Unit,
    onSignOut: () -> Unit,
    onNavigateHome: () -> Unit = {},
    onNavigateCarePlan: () -> Unit = {},
    onNavigateJournal: () -> Unit = {}
) {
    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Profile & Settings",
                onBack = onBack
            )
        },
        bottomBar = {
            SmaBottomNavigationBar(
                currentDestination = SmaNavDestination.PROFILE,
                onNavigate = { dest ->
                    when (dest) {
                        SmaNavDestination.HOME -> onNavigateHome()
                        SmaNavDestination.CARE_PLAN -> onNavigateCarePlan()
                        SmaNavDestination.WELLBEING -> onNavigateJournal()
                        SmaNavDestination.PROFILE -> {}
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Patient Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(SagePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "EV",
                            color = OnSagePrimary,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Elena Vance",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        Text(
                            text = "SMA Type 2 • India Registry ID: SMA-IND-8841",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnWarmStoneVariant
                        )
                        Spacer(Modifier.height(6.dp))
                        SmaStatusChip(
                            text = "Primary Account • Local Demo",
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }
                }
            }

            // Settings Navigation Sections
            Text(
                text = "Preferences & Management",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    SettingNavigationRow(
                        icon = Icons.Default.Accessibility,
                        title = "Accessibility Settings",
                        subtitle = "High contrast, generous touch targets & text scaling",
                        onClick = onOpenAccessibility
                    )
                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    SettingNavigationRow(
                        icon = Icons.Default.Security,
                        title = "Privacy & Consent Centre",
                        subtitle = "MoveTrack camera consent & data sharing choices",
                        onClick = onOpenPrivacy
                    )
                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    SettingNavigationRow(
                        icon = Icons.Default.Notifications,
                        title = "Notification Preferences",
                        subtitle = "Care task alerts, check-in reminders & visit notices",
                        onClick = onOpenNotifications
                    )
                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    SettingNavigationRow(
                        icon = Icons.Default.Group,
                        title = "Connected CareCircle",
                        subtitle = "Manage family and authorized caregiver permissions",
                        onClick = onOpenCaregivers
                    )
                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    SettingNavigationRow(
                        icon = Icons.Default.Lock,
                        title = "Account Security & Credentials",
                        subtitle = "Local passcode, biometric authentication & backup",
                        onClick = onOpenSecurity
                    )
                }
            }

            // Sign out / Switch role button
            SmaOutlinedButton(
                text = "Switch Role or Sign Out",
                leadingIcon = Icons.Default.Logout,
                onClick = onSignOut
            )

            // Bottom clearance so content is never clipped by bottom navigation
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun SettingNavigationRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(SurfaceContainerHigh, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SagePrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = OnWarmStone
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnWarmStoneVariant
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = OutlineColor
        )
    }
}

