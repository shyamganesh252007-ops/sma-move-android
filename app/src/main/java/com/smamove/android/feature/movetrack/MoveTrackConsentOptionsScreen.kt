
package com.smamove.android.feature.movetrack

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ConsentBg = Color(0xFFF5F8FC)
private val ConsentInk = Color(0xFF10243A)
private val ConsentMuted = Color(0xFF586B7B)
private val ConsentTeal = Color(0xFF087F76)
private val ConsentTealDark = Color(0xFF06665F)
private val ConsentSoft = Color(0xFFE5F4F1)

@Composable
fun MoveTrackConsentOptionsScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val store = remember(context) {
        MoveTrackConsentStore(context)
    }

    var cameraConsent by remember {
        mutableStateOf(false)
    }

    var caregiverSharing by remember {
        mutableStateOf(false)
    }

    var preferencesLoaded by remember {
        mutableStateOf(false)
    }

    var isSaving by remember {
        mutableStateOf(false)
    }

    // Restore previously saved choices when the screen opens.
    LaunchedEffect(store) {
        val saved = store.getChoices()

        cameraConsent = saved?.cameraOptIn ?: false
        caregiverSharing = saved?.caregiverSharingOptIn ?: false
        preferencesLoaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ConsentBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "SMA-MOVE  /  ETHICS SHIELD",
            color = ConsentTealDark,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(
                text = "Your data.\nYour decision.",
                color = ConsentInk,
                fontSize = 32.sp,
                lineHeight = 39.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Review each choice carefully. Camera analysis " +
                        "and caregiver sharing are separate decisions.",
                color = ConsentMuted,
                fontSize = 16.sp,
                lineHeight = 25.sp
            )
        }

        ConsentCard(
            number = "01",
            title = "Camera-based movement analysis",
            description = "The planned feature may use camera frames " +
                    "to estimate selected body landmarks. The model is " +
                    "not connected yet, and this screen does not request camera access."
        ) {
            ConsentCheck(
                checked = cameraConsent,
                enabled = preferencesLoaded && !isSaving,
                onCheckedChange = { cameraConsent = it },
                title = "I understand and opt in to the planned " +
                        "camera-based feature.",
                description = "This records your prototype preference " +
                        "only. It does not grant Android camera permission " +
                        "or start a movement session."
            )
        }

        ConsentCard(
            number = "02",
            title = "Storage and privacy",
            description = "The intended design prioritises on-device " +
                    "processing. Before real use, SMA-Move must disclose " +
                    "what data is stored, how it is protected, how long it " +
                    "is retained, and whether any data leaves the device."
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .size(8.dp)
                        .background(ConsentTeal, CircleShape)
                )

                Text(
                    text = "Preferences are stored locally. This " +
                            "prototype has not yet implemented encrypted " +
                            "consent storage or a complete retention policy.",
                    color = ConsentMuted,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        ConsentCard(
            number = "03",
            title = "Caregiver sharing",
            description = "Sharing information with a family member " +
                    "or caregiver should be optional and limited to " +
                    "records the patient chooses to share."
        ) {
            ConsentCheck(
                checked = caregiverSharing,
                enabled = preferencesLoaded && !isSaving,
                onCheckedChange = { caregiverSharing = it },
                title = "I would like to explore optional caregiver sharing.",
                description = "This preference is saved locally only. " +
                        "No records are shared by selecting this box."
            )
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "You can change your mind",
                    color = ConsentInk,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Untick either choice to withdraw that " +
                            "preference, then save again. Declining camera " +
                            "analysis does not prevent you from using " +
                            "other SMA-Move features.",
                    color = ConsentMuted,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            }
        }

        Button(
            onClick = {
                isSaving = true

                try {
                    store.saveChoices(
                        cameraOptIn = cameraConsent,
                        caregiverSharingOptIn = caregiverSharing
                    )

                    Toast.makeText(
                        context,
                        "Preferences saved on this device.",
                        Toast.LENGTH_SHORT
                    ).show()

                    onContinue()
                } catch (exception: Exception) {
                    Toast.makeText(
                        context,
                        "Could not save preferences. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    isSaving = false
                }
            },
            enabled = preferencesLoaded && !isSaving,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(17.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ConsentTealDark,
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFCBD8D9),
                disabledContentColor = Color(0xFF53666B)
            )
        ) {
            Text(
                text = when {
                    !preferencesLoaded -> "Loading preferences..."
                    isSaving -> "Saving..."
                    else -> "Save preferences"
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        OutlinedButton(
            onClick = onBack,
            enabled = !isSaving,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(17.dp)
        ) {
            Text(
                text = "Not now — go back",
                color = ConsentInk,
                fontSize = 14.sp
            )
        }

        Text(
            text = "Prototype only: preferences are saved locally. " +
                    "Camera permission is not requested and no data is shared.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            color = ConsentMuted,
            fontSize = 12.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ConsentCard(
    number: String,
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(13.dp),
                    color = ConsentSoft
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = number,
                            color = ConsentTealDark,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = title,
                    color = ConsentInk,
                    fontSize = 17.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = description,
                color = ConsentMuted,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            content()
        }
    }
}

@Composable
private fun ConsentCheck(
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                ConsentSoft.copy(alpha = 0.65f),
                RoundedCornerShape(15.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked = checked,
            enabled = enabled,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = ConsentTealDark,
                uncheckedColor = ConsentMuted,
                checkmarkColor = Color.White
            )
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 9.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                color = ConsentInk,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = description,
                color = ConsentMuted,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}
