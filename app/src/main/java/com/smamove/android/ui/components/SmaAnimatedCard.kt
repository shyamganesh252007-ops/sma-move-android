
package com.smamove.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import android.provider.Settings
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

/**
 * SMA-Move reusable animated card.
 *
 * Gentle entrance animation with reduced-motion support.
 * The card content remains accessible and interactive.
 */
@Composable
fun SmaAnimatedCard(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    delayMillis: Int = 0,
    cornerRadius: Int = 24,
    content: @Composable BoxScope.() -> Unit
) {
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

    val reducedMotion = animationScale <= 0f

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = if (reducedMotion) {
            fadeIn(
                animationSpec = tween(durationMillis = 0)
            )
        } else {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 280,
                    delayMillis = delayMillis,
                    easing = FastOutSlowInEasing
                )
            ) + slideInVertically(
                animationSpec = tween(
                    durationMillis = 280,
                    delayMillis = delayMillis,
                    easing = FastOutSlowInEasing
                ),
                initialOffsetY = { 8 }
            )
        }
    ) {
        Box(
            modifier = Modifier,
            content = content
        )
    }
}
