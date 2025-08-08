package com.alkursi.design.theme

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    error = Error,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    error = Error,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun FlashNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
    CompositionLocalProvider(
        LocalExtraColors provides extraColors
    ){
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

object AppGlobalTheme {
    val spacing = Spacing()
    val dimensions = Dimensions()
    val typography
        @Composable get() = MaterialTheme.typography
    val colorScheme
        @Composable get() = MaterialTheme.colorScheme
}

@Immutable
data class Spacing(
    val thin: Dp = 4.dp,
    val smallest: Dp = 8.dp,
    val small: Dp = 12.dp,
    val medium: Dp = 14.dp,
    val normal: Dp = 16.dp,
    val large: Dp = 18.dp,
    val extraLarge: Dp = 20.dp,
    val big: Dp = 22.dp,
    val largeBig: Dp = 24.dp,
    val extraBig: Dp = 26.dp,
    val biggest: Dp = 28.dp,
    val huge: Dp = 32.dp,
    val largeHuge: Dp = 34.dp,
    val extraHuge: Dp = 36.dp,
    val hugeBig: Dp = 38.dp,
    val largest: Dp = 40.dp
)

@Immutable
data class Dimensions(
    val none: Dp = 0.dp,
    val smallCardWidth: Dp = 1.dp,
    val smallCardBorder: Dp = 16.dp,
    val smallElevation: Dp = 4.dp,
)