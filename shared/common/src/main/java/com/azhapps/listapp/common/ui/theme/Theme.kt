package com.azhapps.listapp.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = DarkPrimary,
    primaryVariant = DarkPrimaryVariant,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    secondaryVariant = DarkSecondaryVariant
)

private val LightColorPalette = lightColors(
    primary = LightPrimary,
    primaryVariant = LightPrimaryVariant,
    onPrimary = LightOnPrimary,
    secondary = LightSecondary,
    secondaryVariant = LightSecondaryVariant,
)

@Composable
fun ListAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

object ListAppTheme {
    val defaultSpacing = 12.dp

    var alternateBackgroundColor = LightAlternateBackground

    fun init(isSystemInDarkTheme: Boolean) {
        if (isSystemInDarkTheme) {
            alternateBackgroundColor = DarkAlternateBackground
        }
    }
}