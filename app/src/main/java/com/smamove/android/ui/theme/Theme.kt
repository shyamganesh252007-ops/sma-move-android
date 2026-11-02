package com.smamove.android.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = SagePrimary,
    onPrimary = OnSagePrimary,
    primaryContainer = SagePrimaryContainer,
    onPrimaryContainer = OnSagePrimaryContainer,
    inversePrimary = InversePrimary,
    secondary = DeepOliveSecondary,
    onSecondary = OnDeepOliveSecondary,
    secondaryContainer = OliveSecondaryContainer,
    onSecondaryContainer = OnOliveSecondaryContainer,
    tertiary = TerracottaTertiary,
    onTertiary = OnTerracottaTertiary,
    tertiaryContainer = TerracottaTertiaryContainer,
    onTertiaryContainer = OnTerracottaTertiaryContainer,
    background = WarmStoneBackground,
    onBackground = OnWarmStone,
    surface = WarmStoneBackground,
    onSurface = OnWarmStone,
    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = OnWarmStoneVariant,
    surfaceTint = SurfaceTintColor,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    error = ClinicalError,
    onError = OnClinicalError,
    errorContainer = ClinicalErrorContainer,
    onErrorContainer = OnClinicalErrorContainer,
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor
)

private val DarkColorScheme = darkColorScheme(
    primary = SagePrimaryFixedDim,
    onPrimary = OnSagePrimaryFixed,
    primaryContainer = SagePrimary,
    onPrimaryContainer = OnSagePrimaryContainer,
    inversePrimary = SagePrimary,
    secondary = OliveSecondaryFixedDim,
    onSecondary = OnOliveSecondaryFixed,
    secondaryContainer = DeepOliveSecondary,
    onSecondaryContainer = OnOliveSecondaryContainer,
    tertiary = TerracottaTertiaryFixedDim,
    onTertiary = OnTerracottaTertiaryFixed,
    tertiaryContainer = TerracottaTertiary,
    onTertiaryContainer = OnTerracottaTertiaryContainer,
    background = InverseSurface,
    onBackground = InverseOnSurface,
    surface = InverseSurface,
    onSurface = InverseOnSurface,
    surfaceVariant = OnWarmStoneVariant,
    onSurfaceVariant = OutlineVariantColor,
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor,
    error = ClinicalError,
    onError = OnClinicalError,
    errorContainer = ClinicalErrorContainer,
    onErrorContainer = OnClinicalErrorContainer
)

@Composable
fun SMAAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
