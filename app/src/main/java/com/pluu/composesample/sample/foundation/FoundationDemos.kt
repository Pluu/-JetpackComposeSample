package com.pluu.composesample.sample.foundation

import com.pluu.composesample.demo.ComposableDemo
import com.pluu.composesample.demo.DemoCategory

val FoundationDemos = DemoCategory(
    "Foundation in Android",
    listOf(
        ComposableDemo("LazyGridFor") { LazyGridForApp() },
        ComposableDemo("ProgressIndicator Multiple Color") { ProgressIndicatorApp() },
    )
)