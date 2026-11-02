package com.smamove.android.feature.accessibility

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaOutlinedButton
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessibilitySettingsScreen(
    onBack: () -> Unit,
    onOpenLivePreview: () -> Unit
) {
    var textScaling by remember { mutableStateOf(true) }
    var highContrast by remember { mutableStateOf(false) }
    var reducedMotion by remember { mutableStateOf(true) }
    var generousTouchTargets by remember { mutableStateOf(true) }
    var singleTapConfirmation by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Accessibility Settings",
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
            // Intro Banner
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
                            imageVector = Icons.Default.AccessibilityNew,
                            contentDescription = null,
                            tint = OnSagePrimary
                        )
                    }

                    Column {
                        Text(
                            text = "Comfort & Accessibility First",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnSagePrimaryFixed
                        )
                        Text(
                            text = "Engineered for varying muscle strength, tremor, reduced range of motion, and fatigue.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSagePrimaryFixedVariant
                        )
                    }
                }
            }

            // Vision & Display Section
            Text(
                text = "Vision & Display",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AccessibilityToggleRow(
                        icon = Icons.Default.FormatSize,
                        title = "Enhanced Font Scaling (+15%)",
                        subtitle = "Increases body and label text sizing for effortless reading.",
                        checked = textScaling,
                        onCheckedChange = { textScaling = it }
                    )

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    AccessibilityToggleRow(
                        icon = Icons.Default.Contrast,
                        title = "High Contrast Mode",
                        subtitle = "Sharpens borders and deepens charcoal text against warm surfaces.",
                        checked = highContrast,
                        onCheckedChange = { highContrast = it }
                    )

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    AccessibilityToggleRow(
                        icon = Icons.Default.MotionPhotosOff,
                        title = "Reduced Motion & Transitions",
                        subtitle = "Eliminates rapid transitions and sliding animations.",
                        checked = reducedMotion,
                        onCheckedChange = { reducedMotion = it }
                    )
                }
            }

            // Live Contrast Preview Shortcut
            SmaOutlinedButton(
                text = "Live Text Size & Contrast Preview",
                leadingIcon = Icons.Default.Preview,
                onClick = onOpenLivePreview
            )

            // Motor & Dexterity Section
            Text(
                text = "Motor & Touch Targets",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AccessibilityToggleRow(
                        icon = Icons.Default.TouchApp,
                        title = "Generous Touch Targets (52dp+)",
                        subtitle = "Expands all interactive buttons and check boxes for easier tapping.",
                        checked = generousTouchTargets,
                        onCheckedChange = { generousTouchTargets = it }
                    )

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    AccessibilityToggleRow(
                        icon = Icons.Default.CheckCircleOutline,
                        title = "Single-Tap Task Completion",
                        subtitle = "Avoids swiping, drag-and-drop, or multi-finger gestures.",
                        checked = singleTapConfirmation,
                        onCheckedChange = { singleTapConfirmation = it }
                    )
                }
            }

            // Screen Reader Note
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = SurfaceContainerHigh
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.RecordVoiceOver,
                        contentDescription = null,
                        tint = SagePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "TalkBack Support: Every screen has explicit semantics, accessible state descriptions (e.g. Completed vs Not Completed), and logical reading orders.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnWarmStoneVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            // Save confirmation
            SmaPrimaryButton(
                text = "Apply Accessibility Preferences",
                leadingIcon = Icons.Default.Check,
                onClick = onBack
            )
        }
    }
}

@Composable
private fun AccessibilityToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(SurfaceContainerHigh, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SagePrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column {
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
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = OnSagePrimary,
                checkedTrackColor = SagePrimary
            )
        )
    }
}

