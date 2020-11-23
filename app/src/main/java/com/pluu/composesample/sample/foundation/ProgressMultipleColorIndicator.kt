package com.pluu.composesample.sample.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pluu.compose.ui.CircularProgressIndicator
import com.pluu.compose.ui.graphics.toColor

@Composable
fun ProgressIndicatorApp() {
    val list = listOf(
        0xFF0F9D58.toColor(),
        0xFFDB4437.toColor(),
        0xFF4285f4.toColor(),
        0xFFF4B400.toColor()
    )

    Box(alignment = Alignment.Center) {
        CircularProgressIndicator(
            colors = list,
            strokeWidth = 10.dp,
            modifier = Modifier
                .preferredSize(180.dp)
                .padding(4.dp)
        )
    }
}