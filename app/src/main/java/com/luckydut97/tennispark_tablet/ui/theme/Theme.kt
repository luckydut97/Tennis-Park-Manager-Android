package com.luckydut97.tennispark_tablet.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val TennisDarkColorScheme = darkColorScheme(
    primary = TennisGreen,
    secondary = TennisLightGreen,
    tertiary = TennisYellow,
    background = TennisGreen,
    surface = White,
    onPrimary = White,
    onSecondary = TennisGreen,
    onTertiary = TennisGreen,
    onBackground = White,
    onSurface = TennisGreen
)

private val TennisLightColorScheme = lightColorScheme(
    primary = TennisGreen,
    secondary = TennisLightGreen,
    tertiary = TennisYellow,
    background = TennisGreen,
    surface = White,
    onPrimary = White,
    onSecondary = TennisGreen,
    onTertiary = TennisGreen,
    onBackground = White,
    onSurface = TennisGreen
)

@Composable
fun Tennispark_tabletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled for consistent branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> TennisDarkColorScheme
        else -> TennisLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = TennisGreen.toArgb()
            window.navigationBarColor = TennisGreen.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}