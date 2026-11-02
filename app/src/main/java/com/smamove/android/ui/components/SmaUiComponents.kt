
package com.smamove.android.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smamove.android.ui.theme.*

// ============================================================
// TOP APP BAR
// ============================================================

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
                    style = if (title.length > 20) {
                        MaterialTheme.typography.titleMedium
                    } else {
                        MaterialTheme.typography.titleLarge
                    },
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
                        onClick = onOpenAccessibility
                    ) {
                        Icon(
                            imageVector = Icons.Default.Accessibility,
                            contentDescription = "Accessibility settings",
                            tint = OnWarmStoneVariant
                        )
                    }
                }

                if (onOpenPrivacy != null) {
                    IconButton(
                        onClick = onOpenPrivacy
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Privacy centre",
                            tint = OnWarmStoneVariant
                        )
                    }
                }

                if (onOpenProfile != null) {
                    IconButton(
                        onClick = onOpenProfile
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

// ============================================================
// NAVIGATION DESTINATIONS
// ============================================================

enum class SmaNavDestination {
    HOME,
    CARE_PLAN,
    WELLBEING,
    PROFILE
}

// ============================================================
// LIQUID GLASS BOTTOM NAVIGATION
// ============================================================

@Composable
fun SmaBottomNavigationBar(
    currentDestination: SmaNavDestination,
    onNavigate: (SmaNavDestination) -> Unit
) {
    val destinations = listOf(
        Triple(
            SmaNavDestination.HOME,
            Icons.Default.Home,
            "Home"
        ),
        Triple(
            SmaNavDestination.CARE_PLAN,
            Icons.Default.Assignment,
            "Care Plan"
        ),
        Triple(
            SmaNavDestination.WELLBEING,
            Icons.Default.Favorite,
            "Wellbeing"
        ),
        Triple(
            SmaNavDestination.PROFILE,
            Icons.Default.AccountCircle,
            "Profile"
        )
    )

    val selectedIndex = destinations.indexOfFirst {
        it.first == currentDestination
    }.coerceAtLeast(0)

    var dragProgress by remember {
        mutableFloatStateOf(selectedIndex.toFloat())
    }

    var isDragging by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(selectedIndex) {
        if (!isDragging) {
            dragProgress = selectedIndex.toFloat()
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = if (isDragging) {
            dragProgress
        } else {
            selectedIndex.toFloat()
        },
        animationSpec = tween(
            durationMillis = 450,
            easing = FastOutSlowInEasing
        ),
        label = "LiquidGlassMovement"
    )

    Surface(
        color = WarmStoneBackground,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
                .height(76.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFEAF3F1),
                            Color(0xFFDCEAE8),
                            Color(0xFFE8F0EE)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFD0E1DE),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 4.dp)
                .pointerInput( selectedIndex) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                            dragProgress = selectedIndex.toFloat()
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()

                            val tabWidthPx =
                                size.width.toFloat() / destinations.size

                            dragProgress = (
                                    dragProgress +
                                            dragAmount.x / tabWidthPx
                                    ).coerceIn(
                                    0f,
                                    (destinations.size - 1).toFloat()
                                )
                        },
                        onDragEnd = {
                            val targetIndex =
                                dragProgress
                                    .toInt()
                                    .let { lower ->
                                        val fraction =
                                            dragProgress - lower

                                        if (fraction >= 0.5f) {
                                            lower + 1
                                        } else {
                                            lower
                                        }
                                    }
                                    .coerceIn(
                                        0,
                                        destinations.lastIndex
                                    )

                            isDragging = false
                            dragProgress = targetIndex.toFloat()

                            onNavigate(
                                destinations[targetIndex].first
                            )
                        },
                        onDragCancel = {
                            isDragging = false
                            dragProgress = selectedIndex.toFloat()
                        }
                    )
                }
        ) {
            val tabWidth = maxWidth / destinations.size
            val density = LocalDensity.current

            val lensOffset = tabWidth * animatedProgress

            // ------------------------------------------------
            // NAVIGATION ITEMS
            // ------------------------------------------------

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                destinations.forEachIndexed {
                        index, destination ->

                    val distance = kotlin.math.abs(
                        animatedProgress - index
                    )

                    val proximity = (
                            1f - distance
                            ).coerceIn(0f, 1f)

                    val scale = 1f + (0.28f * proximity)

                    val iconScale by animateFloatAsState(
                        targetValue = scale,
                        animationSpec = tween(
                            durationMillis = 100,
                            easing = FastOutSlowInEasing
                        ),
                        label = "NavigationZoom$index"
                    )

                    SmaNavItem(
                        modifier = Modifier.weight(1f),
                        icon = destination.second,
                        label = destination.third,
                        isSelected = index == selectedIndex,
                        scale = iconScale,
                        onClick = {
                            isDragging = false
                            onNavigate(destination.first)
                        }
                    )
                }
            }

            // ------------------------------------------------
            // MOVING GLASS LENS
            // ------------------------------------------------

            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = lensOffset + 4.dp)
                    .width(tabWidth - 8.dp)
                    .height(64.dp)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(25.dp)
                        shadowElevation = 9.dp.toPx()
                    }
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xCCFFFFFF),
                                Color(0x99D9E8E8),
                                Color(0x66B8D9DB),
                                Color(0xCCFFFFFF)
                            )
                        ),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .border(
                        width = 1.4.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE5FFFF),
                                Color(0xFF74DCEB),
                                Color(0xFF8B9EFF),
                                Color(0xAAFFFFFF),
                                Color(0xFFB4F2E9)
                            )
                        ),
                        shape = RoundedCornerShape(25.dp)
                    )
            ) {
                // Glass reflection.
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(0.72f)
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White,
                                    Color.Transparent
                                )
                            ),
                            CircleShape
                        )
                )

                // Subtle lower reflection.
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(0.55f)
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF9BDDEB),
                                    Color.Transparent
                                )
                            ),
                            CircleShape
                        )
                )
            }
        }
    }
}

// ============================================================
// INDIVIDUAL NAVIGATION ITEM
// ============================================================

@Composable
private fun SmaNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    scale: Float,
    onClick: () -> Unit
) {
    val iconColor = Color(0xFF426B78)
    val labelColor = Color(0xFF365965)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                role = Role.Tab,
                onClick = onClick
            )
            .semantics {
                contentDescription = "$label navigation tab"
                role = Role.Tab
            }
            .padding(
                horizontal = 1.dp,
                vertical = 4.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = labelColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ============================================================
// STATUS CHIP
// ============================================================

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
            .background(
                containerColor,
                RoundedCornerShape(100.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
            ),
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

// ============================================================
// PRIMARY BUTTON
// ============================================================

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

// ============================================================
// SECONDARY BUTTON
// ============================================================

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

// ============================================================
// OUTLINED BUTTON
// ============================================================

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
            brush = androidx.compose.ui.graphics.SolidColor(
                OutlineVariantColor
            )
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
