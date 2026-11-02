package com.smamove.android.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmaTopAppBar(
    title: String = "SMA-Move",
    onBack: (() -> Unit)? = null,
    onOpenAccessibility: (() -> Unit)? = null,
    onOpenPrivacy: (() -> Unit)? = null,
    onOpenProfile: (() -> Unit)? = null
) {
    Surface(
        color = WarmStoneBackground,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = if (title.length > 20) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(48.dp)
                            .semantics {
                                contentDescription = "Go back"
                                role = Role.Button
                            }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = OnWarmStone
                        )
                    }
                }
            },
            actions = {
                if (onOpenAccessibility != null) {
                    IconButton(
                        onClick = onOpenAccessibility,
                        modifier = Modifier
                            .size(48.dp)
                            .semantics {
                                contentDescription = "Accessibility settings"
                                role = Role.Button
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Accessibility,
                            contentDescription = null,
                            tint = OnWarmStoneVariant
                        )
                    }
                }
                if (onOpenPrivacy != null) {
                    IconButton(
                        onClick = onOpenPrivacy,
                        modifier = Modifier
                            .size(48.dp)
                            .semantics {
                                contentDescription = "Privacy centre and consent"
                                role = Role.Button
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = OnWarmStoneVariant
                        )
                    }
                }
                if (onOpenProfile != null) {
                    IconButton(
                        onClick = onOpenProfile,
                        modifier = Modifier
                            .size(48.dp)
                            .semantics {
                                contentDescription = "Profile and settings"
                                role = Role.Button
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(SagePrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = OnSagePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = WarmStoneBackground,
                titleContentColor = OnWarmStone
            )
        )
    }
}

enum class SmaNavDestination {
    HOME, CARE_PLAN, WELLBEING, PROFILE
}

@Composable
fun SmaBottomNavigationBar(
    currentDestination: SmaNavDestination,
    onNavigate: (SmaNavDestination) -> Unit
) {
    Surface(
        color = WarmStoneBackground.copy(alpha = 0.98f),
        tonalElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(68.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmaNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = currentDestination == SmaNavDestination.HOME,
                onClick = { onNavigate(SmaNavDestination.HOME) }
            )
            SmaNavItem(
                icon = Icons.Default.Assignment,
                label = "Care Plan",
                isSelected = currentDestination == SmaNavDestination.CARE_PLAN,
                onClick = { onNavigate(SmaNavDestination.CARE_PLAN) }
            )
            SmaNavItem(
                icon = Icons.Default.Favorite,
                label = "Wellbeing",
                isSelected = currentDestination == SmaNavDestination.WELLBEING,
                onClick = { onNavigate(SmaNavDestination.WELLBEING) }
            )
            SmaNavItem(
                icon = Icons.Default.AccountCircle,
                label = "Profile",
                isSelected = currentDestination == SmaNavDestination.PROFILE,
                onClick = { onNavigate(SmaNavDestination.PROFILE) }
            )
        }
    }
}

@Composable
private fun SmaNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedContainerColor by animateColorAsState(
        targetValue = if (isSelected) OliveSecondaryContainer else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "NavItemBg"
    )
    val animatedContentColor by animateColorAsState(
        targetValue = if (isSelected) OnOliveSecondaryContainer else OnWarmStoneVariant,
        animationSpec = tween(durationMillis = 200),
        label = "NavItemColor"
    )

    Column(
        modifier = Modifier
            .sizeIn(minWidth = 56.dp, minHeight = 48.dp)
            .clip(CircleShape)
            .clickable(role = Role.Tab, onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .semantics {
                contentDescription = "$label navigation tab"
                role = Role.Tab
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(animatedContainerColor, RoundedCornerShape(12.dp))
                .padding(horizontal = 14.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = animatedContentColor,
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = label,
            color = animatedContentColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
fun SmaStatusChip(
    text: String,
    icon: ImageVector? = null,
    containerColor: Color = OliveSecondaryContainer,
    contentColor: Color = OnOliveSecondaryContainer
) {
    Row(
        modifier = Modifier
            .heightIn(min = 28.dp)
            .background(containerColor, RoundedCornerShape(100.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(14.dp)
            )
        }
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SmaPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SagePrimary,
            contentColor = OnSagePrimary,
            disabledContainerColor = SurfaceContainerHigh,
            disabledContentColor = OutlineColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SmaSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceContainerHigh,
            contentColor = OnWarmStone,
            disabledContainerColor = SurfaceContainerLow,
            disabledContentColor = OutlineColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = OnWarmStone,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SmaOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = OnWarmStone
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.ui.graphics.SolidColor(OutlineVariantColor)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = OnWarmStone,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

