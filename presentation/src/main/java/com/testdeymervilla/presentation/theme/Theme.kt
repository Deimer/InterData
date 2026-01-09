package com.testdeymervilla.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Immutable
data class ExtendedColors(
    val primaryButton: Color,
    val secondaryButton: Color,
    val miniButton: Color,
    val onPrimaryButton: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        primaryButton = Color.Unspecified,
        secondaryButton = Color.Unspecified,
        miniButton = Color.Unspecified,
        onPrimaryButton = Color.Unspecified
    )
}

private val DarkExtendedColors = ExtendedColors(
    primaryButton = Purple40,
    secondaryButton = Purple40,
    miniButton = DeepPurple,
    onPrimaryButton = Snow
)

private val LightExtendedColors = ExtendedColors(
    primaryButton = CyanSkyDark,
    secondaryButton = CyanSkyDark,
    miniButton = DeepOcean,
    onPrimaryButton = Snow
)

private val DarkColorScheme = darkColorScheme(
    primary = Snow,
    onPrimary = Dark,
    secondary = CyanSkyDark,
    onSecondary = Snow,
    background = ChineseBlack,
    surface = Dark,
    surfaceTint = Purple40,
    tertiary = White60,
    onTertiary = White40,
    tertiaryContainer = White80,
    onTertiaryContainer = White60,
    scrim = White40,
)

private val LightColorScheme = lightColorScheme(
    primary = Dark,
    onPrimary = Snow,
    secondary = Snow,
    onSecondary = CyanSkyDark,
    background = White60,
    surface = Snow,
    surfaceTint = CyanSkyDark,
    tertiary = White40,
    onTertiary = White60,
    tertiaryContainer = White60,
    onTertiaryContainer = White80,
    scrim = Dark
)

object InterDataTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}

@Composable
fun InterDataTheme(
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

    val extendedColors = if(darkTheme) DarkExtendedColors else LightExtendedColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) ChineseBlack.toArgb() else White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}