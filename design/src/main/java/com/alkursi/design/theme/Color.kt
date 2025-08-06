package com.alkursi.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

//dark
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

//light
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

//others
val Error = Color(0xFFF44336)


data class ExtraColors(
    val green: Color
)

internal val LocalExtraColors = staticCompositionLocalOf {
    ExtraColors(
        green = Color(0xFF4CAF50)
    )
}

val extraColors: ExtraColors
    @Composable get() = LocalExtraColors.current