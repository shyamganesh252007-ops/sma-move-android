
package com.smamove.android.ui.animations

import android.provider.Settings
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * SMA-Move Motion Design System
 *
 * Centralized animation timings and easing.
 * Designed for calm, accessible healthcare interactions.
 */
object SmaMotion {

    // Animation durations (milliseconds)
    const val INSTANT = 100
    const val FAST = 150
    const val STANDARD = 220
    const val CARD = 280
    const val PROGRESS = 400

    // Small movement distances (dp)
    const val CARD_OFFSET = 8
    const val PRESS_SCALE = 0.98f

    // Standard easing curves
    val STANDARD_EASING = FastOutSlowInEasing
    val ENTRANCE_EASING = LinearOutSlowInEasing
}

/**
 * Respects the Android system animator duration scale.
 * When system animations are disabled, returns zero duration.
 */
@Composable
fun rememberSmaMotionDuration(
    durationMillis: Int
): Int {
    val context = LocalContext.current

    val animationScale = remember(context) {
        try {
            Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.ANIMATOR_DURATION_SCALE,
                1f
            )
        } catch (_: Exception) {
            1f
        }
    }

    return if (animationScale <= 0f) {
        0
    } else {
        (durationMillis * animationScale).toInt()
    }
}
