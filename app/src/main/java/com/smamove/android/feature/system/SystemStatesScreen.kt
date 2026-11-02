package com.smamove.android.feature.system

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaSecondaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemStatesScreen(
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("empty") }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "System States & Resilience",
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
            // Tab Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerHigh, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterTabItem("Empty", selectedTab == "empty") { selectedTab = "empty" }
                FilterTabItem("Loading", selectedTab == "loading") { selectedTab = "loading" }
                FilterTabItem("Offline", selectedTab == "offline") { selectedTab = "offline" }
                FilterTabItem("Demo Notice", selectedTab == "demo") { selectedTab = "demo" }
            }

            when (selectedTab) {
                "empty" -> EmptyStateContent()
                "loading" -> LoadingStateContent()
                "offline" -> OfflineStateContent()
                "demo" -> DemoDisclaimerContent()
            }
        }
    }
}

@Composable
private fun RowScope.FilterTabItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .weight(1f)
            .height(40.dp),
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) WarmStoneBackground else Color.Transparent,
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) OnWarmStone else OnWarmStoneVariant
            )
        }
    }
}

@Composable
private fun EmptyStateContent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(OliveSecondaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AssignmentLate,
                    contentDescription = null,
                    tint = OnOliveSecondaryContainer,
                    modifier = Modifier.size(36.dp)
                )
            }

            Text(
                text = "No Active Care Tasks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Text(
                text = "No prescribed care plan tasks were found for today. You can request routine updates from your clinical care coordinator.",
                style = MaterialTheme.typography.bodyMedium,
                color = OnWarmStoneVariant,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            SmaPrimaryButton(
                text = "Check for Updated Plan",
                leadingIcon = Icons.Default.Sync,
                onClick = {}
            )
        }
    }
}

@Composable
private fun LoadingStateContent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                color = SagePrimary,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = "Synchronizing Care Records",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Text(
                text = "Loading your encrypted local routines and check-in timeline...",
                style = MaterialTheme.typography.bodySmall,
                color = OnWarmStoneVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun OfflineStateContent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(SurfaceContainerHigh, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CloudOff,
                    contentDescription = null,
                    tint = OutlineColor,
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                text = "Local Offline Mode Active",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Text(
                text = "No internet connection detected. You can continue checking off tasks and recording wellbeing check-ins. All changes are preserved on this device.",
                style = MaterialTheme.typography.bodyMedium,
                color = OnWarmStoneVariant,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            SmaSecondaryButton(
                text = "Retry Network Connection",
                leadingIcon = Icons.Default.Refresh,
                onClick = {}
            )
        }
    }
}

@Composable
private fun DemoDisclaimerContent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = TerracottaTertiaryFixed)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = OnTerracottaTertiaryFixed)
                Text(
                    text = "Honest Prototype Disclosure",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnTerracottaTertiaryFixed
                )
            }

            Text(
                text = "• Clinical Guidance: Exercise routines and machine pressures are demo placeholders. Always follow your prescribing physician's directions.\n• Computer Vision: MoveTrack is an experimental research prototype and does not perform clinical diagnosis.\n• Backend: Telehealth video and ABDM verification are local evaluation flows and are not connected to production government registries.",
                style = MaterialTheme.typography.bodySmall,
                color = OnTerracottaTertiaryFixedVariant,
                lineHeight = 20.sp
            )
        }
    }
}

