package com.example.astra.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = AstraCoralPrimary,
    onPrimary = Color.White,
    primaryContainer = AstraLightCoral,
    onPrimaryContainer = AstraDarkCoral,
    secondary = AstraSecondaryCoral,
    onSecondary = Color.White,
    secondaryContainer = AstraLightCoral,
    onSecondaryContainer = AstraDarkCoral,
    tertiary = AstraDarkCoral,
    onTertiary = Color.White,
    background = AstraBackgroundLight,
    onBackground = AstraTextPrimaryLight,
    surface = AstraSurfaceLight,
    onSurface = AstraTextPrimaryLight,
    surfaceVariant = AstraBackgroundLight,
    onSurfaceVariant = AstraTextSecondary,
    outline = AstraBorderLight,
    outlineVariant = AstraBorderLight,
    surfaceContainer = AstraSurfaceLight,
    surfaceContainerHigh = AstraSurfaceLight,
    surfaceContainerHighest = AstraSurfaceLight,
    surfaceContainerLow = AstraBackgroundLight,
    surfaceContainerLowest = AstraBackgroundLight
)

private val DarkColorScheme = darkColorScheme(
    primary = AstraCoralPrimary,
    onPrimary = Color.White,
    primaryContainer = AstraDarkCoral,
    onPrimaryContainer = AstraLightCoral,
    secondary = AstraSecondaryCoral,
    onSecondary = Color.White,
    secondaryContainer = AstraDarkCoral,
    onSecondaryContainer = AstraLightCoral,
    tertiary = AstraLightCoral,
    onTertiary = AstraTextPrimaryDark,
    background = AstraBackgroundDark,
    onBackground = AstraTextPrimaryDark,
    surface = AstraSurfaceDark,
    onSurface = AstraTextPrimaryDark,
    surfaceVariant = AstraBackgroundDark,
    onSurfaceVariant = AstraTextSecondary,
    outline = AstraBorderDark,
    outlineVariant = AstraBorderDark,
    surfaceContainer = AstraSurfaceDark,
    surfaceContainerHigh = AstraSurfaceDark,
    surfaceContainerHighest = AstraSurfaceDark,
    surfaceContainerLow = AstraBackgroundDark,
    surfaceContainerLowest = AstraBackgroundDark
)

@Composable
fun AstraTheme(
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
