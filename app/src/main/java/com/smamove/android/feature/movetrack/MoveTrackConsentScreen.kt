
package com.smamove.android.feature.movetrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val MoveBackground = Color(0xFFF8F9FF)
private val MoveInk = Color(0xFF0B1C30)
private val MoveMuted = Color(0xFF52616B)
private val MoveTeal = Color(0xFF00685F)
private val MoveCard = Color(0xFFEAF3F1)

@Composable
fun MoveTrackConsentScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoveBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "MOVETRACK",
            color = MoveTeal,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )

        Text(
            text = "Your movement, your choice",
            color = MoveInk,
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )

        Text(
            text = "An optional movement session designed " +
                    "around your comfort and preferences.",
            color = MoveMuted,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MoveCard
            ),
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Your privacy comes first",
                    color = MoveTeal,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "The camera will not activate on this screen. " +
                            "Before a session, you will be told what is " +
                            "processed, what is saved, and who can access it.",
                    color = MoveInk,
                    fontSize = 15.sp,
                    lineHeight = 23.sp
                )
            }
        }

        PrivacyPoint(
            title = "You stay in control",
            description = "Starting is optional. You can stop " +
                    "or leave a session at any time."
        )

        PrivacyPoint(
            title = "Designed for privacy",
            description = "We intend to process movement on your " +
                    "device where feasible and avoid raw video uploads " +
                    "by default."
        )

        PrivacyPoint(
            title = "Not a diagnosis",
            description = "Experimental movement estimates are not " +
                    "a diagnosis or a substitute for clinical assessment."
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MoveTeal,
                contentColor = Color.White
            )
        ) {
            Text(
                "Continue to session information",
                fontWeight = FontWeight.SemiBold
            )
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Not now — return to dashboard",
                color = MoveInk
            )
        }

        Text(
            text = "You can use the rest of SMA-Move without " +
                    "using MoveTrack.",
            color = MoveMuted,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
private fun PrivacyPoint(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "✓",
            color = MoveTeal,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                color = MoveInk,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                color = MoveMuted,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }
    }
}
