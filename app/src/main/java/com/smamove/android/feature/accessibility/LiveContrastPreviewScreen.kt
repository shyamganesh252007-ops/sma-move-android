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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaPrimaryButton
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveContrastPreviewScreen(
    onBack: () -> Unit
) {
    var previewFontSize by remember { mutableFloatStateOf(16f) }
    var isHighContrastActive by remember { mutableStateOf(false) }
    var testChecked by remember { mutableStateOf(true) }

    val textColor = if (isHighContrastActive) Color(0xFF000000) else OnWarmStone
    val surfaceColor = if (isHighContrastActive) Color(0xFFFFFFFF) else SurfaceContainerLow
    val primaryBtnColor = if (isHighContrastActive) Color(0xFF38450F) else SagePrimary

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Live Text & Contrast Preview",
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
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Interactive Accessibility Preview",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Adjust text scale and contrast to evaluate legibility in real-time.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant
                )
            }

            // Controls Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Text Size Scale: ${previewFontSize.toInt()} sp",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnWarmStone
                        )
                        SmaStatusChip(
                            text = if (previewFontSize > 18f) "Enlarged" else "Standard",
                            containerColor = SagePrimaryFixed,
                            contentColor = OnSagePrimaryFixed
                        )
                    }

                    Slider(
                        value = previewFontSize,
                        onValueChange = { previewFontSize = it },
                        valueRange = 14f..24f,
                        steps = 5,
                        colors = SliderDefaults.colors(
                            thumbColor = SagePrimary,
                            activeTrackColor = SagePrimary
                        )
                    )

                    HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "High Contrast Mode",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Deep black on pure white (WCAG AAA 7.5:1+)",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant
                            )
                        }

                        Switch(
                            checked = isHighContrastActive,
                            onCheckedChange = { isHighContrastActive = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnSagePrimary,
                                checkedTrackColor = SagePrimary
                            )
                        )
                    }
                }
            }

            // Live Preview Card
            Text(
                text = "Live Screen Component Preview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnWarmStone
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                border = if (isHighContrastActive) androidx.compose.foundation.BorderStroke(2.dp, Color.Black) else null
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Morning BiPAP & Cough Assist",
                            fontSize = (previewFontSize + 2).sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        Checkbox(
                            checked = testChecked,
                            onCheckedChange = { testChecked = it },
                            colors = CheckboxDefaults.colors(checkedColor = primaryBtnColor)
                        )
                    }

                    Text(
                        text = "15 mins duration • Verified by Dr. Sarah Jenkins",
                        fontSize = (previewFontSize - 2).sp,
                        color = if (isHighContrastActive) Color(0xFF222222) else OnWarmStoneVariant
                    )

                    Text(
                        text = "Ensure patient is seated upright at 45 to 90 degrees with head support before starting mechanical insufflation cycles.",
                        fontSize = previewFontSize.sp,
                        lineHeight = (previewFontSize * 1.4f).sp,
                        color = textColor
                    )

                    Button(
                        onClick = { testChecked = !testChecked },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryBtnColor)
                    ) {
                        Text(
                            text = if (testChecked) "Mark as Not Done" else "Mark Task Complete",
                            fontSize = previewFontSize.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Accessibility Verification Badge
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = OliveSecondaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = OnOliveSecondaryContainer
                    )
                    Text(
                        text = "Verified WCAG 2.1 AA Compliant: Minimum contrast ratio exceeds 4.5:1 for body copy and 3:1 for large headlines.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnOliveSecondaryContainer,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            SmaPrimaryButton(
                text = "Done with Preview",
                onClick = onBack
            )
        }
    }
}

