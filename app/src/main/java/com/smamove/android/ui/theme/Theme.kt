package com.smamove.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Teal300,
    onPrimary = Navy900,
    primaryContainer = Color(0xFF164E51),
    onPrimaryContainer = Color(0xFFB6F0E8),
    secondary = Color(0xFFB9CDE0),
    onSecondary = Navy900,
    secondaryContainer = Navy700,
    onSecondaryContainer = Color(0xFFE5EEFA),
    tertiary = Teal300,
    onTertiary = Navy900,
    tertiaryContainer = Color(0xFF244552),
    onTertiaryContainer = Color(0xFFD4F2EE),
    background = Color(0xFF0D1927),
    onBackground = Color(0xFFEAF2FA),
    surface = Color(0xFF13273A),
    onSurface = Color(0xFFEAF2FA),
    surfaceVariant = Color(0xFF1C344B),
    onSurfaceVariant = Color(0xFFC1D0DE),
    outline = Color(0xFF91A5B6),
    outlineVariant = Color(0xFF40566A),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val LightColorScheme = lightColorScheme(
    primary = Teal700,
    onPrimary = White,
    primaryContainer = Color(0xFFD4F2EE),
    onPrimaryContainer = Navy900,
    secondary = Navy700,
    onSecondary = White,
    secondaryContainer = Blue100,
    onSecondaryContainer = Navy900,
    tertiary = Teal300,
    onTertiary = Navy900,
    tertiaryContainer = SurfaceBlue,
    onTertiaryContainer = Navy900,
    background = CanvasBlue,
    onBackground = Navy900,
    surface = White,
    onSurface = Navy900,
    surfaceVariant = Blue100,
    onSurfaceVariant = Navy700,
    outline = MutedBlue,
    outlineVariant = Color(0xFFD2DEE8),
    error = ErrorRed
)

@Composable
fun SMAAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
