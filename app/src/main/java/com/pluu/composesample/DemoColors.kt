package com.pluu.composesample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*

@Stable
class DemoColors {
    var light: Colors by mutableStateOf(lightColors())
    var dark: Colors by mutableStateOf(darkColors())

    @Composable
    val colors
        get() = if (isSystemInDarkTheme()) dark else light
}
