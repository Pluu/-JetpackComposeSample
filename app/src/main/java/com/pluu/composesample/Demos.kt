package com.pluu.composesample

import com.pluu.composesample.demo.DemoCategory
import com.pluu.composesample.sample.modifier.ModifierDemos

/**
 * [DemoCategory] containing all the top level demo categories.
 */
val AllDemosCategory = DemoCategory(
    "Jetpack Compose Demos",
    listOf(
        ModifierDemos
    )
)