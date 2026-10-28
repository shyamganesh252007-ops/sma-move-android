
package com.smamove.android.feature.movetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoveTrackSessionInfoScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val MoveTrackNavy = colors.onSurface
    val MoveTrackTeal = colors.primary
    val MoveTrackMuted = colors.onSurfaceVariant
    val MoveTrackBackground = colors.background

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MoveTrackBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextButton(
                onClick = onBack,
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = MoveTrackNavy
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Back",
                    color = MoveTrackNavy
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(
                            color = MoveTrackTeal.copy(alpha = 0.16f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = MoveTrackNavy,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Before You Begin",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MoveTrackNavy,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Your comfort, privacy and choice come first.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MoveTrackMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 25.sp
                )
            }

            InfoCard(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = MoveTrackTeal
                    )
                },
                title = "You stay in control",
                description = "Camera-based movement tracking is optional. You can decline or withdraw your consent."
            )

            InfoCard(
                icon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MoveTrackTeal
                    )
                },
                title = "No diagnosis",
                description = "This prototype is for research and demonstration. It does not diagnose SMA, assess medical severity or replace professional advice."
            )

            InfoCard(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MoveTrackTeal
                    )
                },
                title = "Prototype limitations",
                description = "Continuing to the next screen does not activate the camera or upload data. Storage and privacy safeguards must be verified before real patient use."
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MoveTrackTeal.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MoveTrackNavy,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    text = "Please continue only if you understand these limitations. You can choose your preferences on the next screen.",
                    color = MoveTrackNavy,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp
                )
            }

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text(
                    text = "Review My Choices",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Your participation is voluntary.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MoveTrackMuted
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun InfoCard(
    icon: @Composable () -> Unit,
    title: String,
    description: String
) {
    val colors = MaterialTheme.colorScheme
    val MoveTrackNavy = colors.onSurface
    val MoveTrackTeal = colors.primary
    val MoveTrackMuted = colors.onSurfaceVariant
    val MoveTrackCard = colors.surface

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MoveTrackCard,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = MoveTrackTeal.copy(alpha = 0.13f),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = MoveTrackNavy,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    color = MoveTrackMuted,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp
                )
            }
        }
    }
}
