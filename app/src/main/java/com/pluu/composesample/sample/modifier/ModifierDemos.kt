package com.pluu.composesample.sample.modifier

import com.pluu.composesample.demo.ComposableDemo
import com.pluu.composesample.demo.DemoCategory

val ModifierDemos = DemoCategory(
    "Modifier in Android",
    listOf(
        ComposableDemo("drawLayout Sample") { DrawLayerApp() }
    )
)