package com.smamove.android.feature.careplan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smamove.android.ui.components.SmaBottomNavigationBar
import com.smamove.android.ui.components.SmaNavDestination
import com.smamove.android.ui.components.SmaStatusChip
import com.smamove.android.ui.components.SmaTopAppBar
import com.smamove.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarePlanScreen(
    viewModel: CarePlanViewModel,
    onBack: () -> Unit,
    onOpenTaskDetails: (Int) -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateJournal: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    val tasks = viewModel.tasks
    val completedCount by remember {
        derivedStateOf { viewModel.completedCount }
    }
    val progress = if (tasks.isEmpty()) 0f else completedCount.toFloat() / tasks.size
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "CarePlanProgress"
    )

    var selectedFilter by remember { mutableStateOf("All") }
    val categories = listOf("All", "Respiratory care", "Positioning", "Hydration", "Wellbeing", "Care coordination")

    val filteredTasks = remember(selectedFilter, tasks) {
        if (selectedFilter == "All") tasks
        else tasks.filter { it.category.contains(selectedFilter, ignoreCase = true) }
    }

    Scaffold(
        containerColor = WarmStoneBackground,
        topBar = {
            SmaTopAppBar(
                title = "Daily Care Plan",
                onBack = onBack
            )
        },
        bottomBar = {
            SmaBottomNavigationBar(
                currentDestination = SmaNavDestination.CARE_PLAN,
                onNavigate = { dest ->
                    when (dest) {
                        SmaNavDestination.HOME -> onNavigateHome()
                        SmaNavDestination.CARE_PLAN -> {}
                        SmaNavDestination.WELLBEING -> onNavigateJournal()
                        SmaNavDestination.PROFILE -> onNavigateProfile()
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
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Context header
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TODAY'S REGIMEN",
                        color = SagePrimary,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    SmaStatusChip(
                        text = "Demo Protocol",
                        icon = Icons.Default.Info,
                        containerColor = SurfaceContainerHigh,
                        contentColor = OnWarmStoneVariant
                    )
                }
                Text(
                    text = "Personalized Care Protocol",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnWarmStone
                )
                Text(
                    text = "Prescribed clinical maintenance routines. Check off tasks as completed or tap instructions. Always follow your prescribing pulmonologist's individualized guidance.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnWarmStoneVariant
                )
            }

            // Progress Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SagePrimaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Daily Progress",
                            color = OnSagePrimaryContainer,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$completedCount of ${tasks.size} done",
                            color = OnSagePrimaryContainer,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = OnSagePrimaryContainer,
                        trackColor = SagePrimary.copy(alpha = 0.4f)
                    )

                    Text(
                        text = "Maintaining consistent respiratory care and gentle position changes preserves comfort and pulmonary health.",
                        color = OnSagePrimaryContainer.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Category Filter Chips (Horizontal Scrollable Row with Zero Excessive Spacing)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                categories.forEach { category ->
                    val isSelected = selectedFilter == category
                    val chipContainerColor by animateColorAsState(
                        targetValue = if (isSelected) SagePrimary else SurfaceContainerLow,
                        animationSpec = tween(durationMillis = 200),
                        label = "ChipBg"
                    )
                    val chipLabelColor by animateColorAsState(
                        targetValue = if (isSelected) OnSagePrimary else OnWarmStoneVariant,
                        animationSpec = tween(durationMillis = 200),
                        label = "ChipLabel"
                    )

                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = chipContainerColor,
                            selectedLabelColor = chipLabelColor,
                            containerColor = chipContainerColor,
                            labelColor = chipLabelColor
                        ),
                        shape = RoundedCornerShape(100.dp),
                        modifier = Modifier.heightIn(min = 40.dp)
                    )
                }
            }

            // Task List Section (Tight, cohesive vertical arrangement)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Prescribed Tasks (${filteredTasks.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnWarmStone
                    )
                    if (selectedFilter != "All") {
                        Text(
                            text = "Filtered by $selectedFilter",
                            style = MaterialTheme.typography.labelSmall,
                            color = OutlineColor
                        )
                    }
                }

                if (filteredTasks.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterListOff,
                                contentDescription = null,
                                tint = OutlineColor,
                                modifier = Modifier.size(36.dp)
                            )
                            Text(
                                text = "No tasks under '$selectedFilter'",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmStone
                            )
                            Text(
                                text = "Select 'All' to view your complete daily care protocol.",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnWarmStoneVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    filteredTasks.forEach { task ->
                        CareTaskCard(
                            task = task,
                            onToggle = { viewModel.toggleTask(task.id) },
                            onOpenDetails = { onOpenTaskDetails(task.id) }
                        )
                    }
                }
            }

            // Bottom clearance so task cards are never clipped by the bottom navigation bar
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun CareTaskCard(
    task: CarePlanTask,
    onToggle: () -> Unit,
    onOpenDetails: () -> Unit
) {
    val cardContainerColor by animateColorAsState(
        targetValue = if (task.isCompleted) SurfaceContainer else SurfaceContainerLow,
        animationSpec = tween(durationMillis = 200),
        label = "TaskCardBg"
    )
    val checkContainerColor by animateColorAsState(
        targetValue = if (task.isCompleted) OliveSecondaryContainer else WarmStoneBackground,
        animationSpec = tween(durationMillis = 200),
        label = "TaskCheckBg"
    )
    val checkmarkAlpha by animateFloatAsState(
        targetValue = if (task.isCompleted) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "CheckAlpha"
    )
    val checkmarkScale by animateFloatAsState(
        targetValue = if (task.isCompleted) 1f else 0.5f,
        animationSpec = tween(durationMillis = 200),
        label = "CheckScale"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardContainerColor)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmaStatusChip(
                        text = task.category,
                        containerColor = if (task.isCompleted) SurfaceContainerHigh else SagePrimaryFixed,
                        contentColor = if (task.isCompleted) OnWarmStoneVariant else OnSagePrimaryFixed
                    )
                    Text(
                        text = task.scheduledTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = OutlineColor
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (task.isCompleted) {
                        SmaStatusChip(
                            text = "Done",
                            icon = Icons.Default.Check,
                            containerColor = OliveSecondaryContainer,
                            contentColor = OnOliveSecondaryContainer
                        )
                    }

                    // Checkbox button with min 48dp touch target and clear accessibility semantics
                    IconButton(
                        onClick = onToggle,
                        modifier = Modifier
                            .size(48.dp)
                            .semantics {
                                contentDescription = "Toggle task: ${task.title}"
                                stateDescription = if (task.isCompleted) "Completed" else "Not completed"
                                role = Role.Checkbox
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(checkContainerColor),
                            contentAlignment = Alignment.Center
                        ) {
                            if (checkmarkAlpha > 0.01f) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = OnOliveSecondaryContainer,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .graphicsLayer {
                                            alpha = checkmarkAlpha
                                            scaleX = checkmarkScale
                                            scaleY = checkmarkScale
                                        }
                                )
                            }
                        }
                    }
                }
            }

            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (task.isCompleted) OnWarmStoneVariant else OnWarmStone,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )

            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                color = OnWarmStoneVariant
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(role = Role.Button, onClick = onOpenDetails)
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = SagePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = task.duration,
                        style = MaterialTheme.typography.labelSmall,
                        color = SagePrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "• ${task.verifiedBy}",
                        style = MaterialTheme.typography.labelSmall,
                        color = OutlineColor
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.labelSmall,
                        color = SagePrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = SagePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
