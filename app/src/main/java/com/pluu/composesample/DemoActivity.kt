package com.pluu.composesample

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.listSaver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.setContent
import com.pluu.composesample.demo.ActivityDemo
import com.pluu.composesample.demo.Demo
import com.pluu.composesample.demo.DemoCategory

class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activityStarter = fun(demo: ActivityDemo<*>) {
                startActivity(Intent(this, demo.activityClass.java))
            }
            val navigator = rememberSavedInstanceState(
                saver = Navigator.Saver(AllDemosCategory, onBackPressedDispatcher, activityStarter)
            ) {
                Navigator(AllDemosCategory, onBackPressedDispatcher, activityStarter)
            }
            val demoColors = remember { DemoColors() }
            DemoTheme(demoColors, window) {
                val filteringMode = rememberSavedInstanceState(
                    saver = FilterMode.Saver(onBackPressedDispatcher)
                ) {
                    FilterMode(onBackPressedDispatcher)
                }
                val onStartFiltering = { filteringMode.isFiltering = true }
                val onEndFiltering = { filteringMode.isFiltering = false }
                DemoApp(
                    currentDemo = navigator.currentDemo,
                    backStackTitle = navigator.backStackTitle,
                    isFiltering = filteringMode.isFiltering,
                    onStartFiltering = onStartFiltering,
                    onEndFiltering = onEndFiltering,
                    onNavigateToDemo = { demo ->
                        if (filteringMode.isFiltering) {
                            onEndFiltering()
                            navigator.popAll()
                        }
                        navigator.navigateTo(demo)
                    },
                    canNavigateUp = !navigator.isRoot,
                    onNavigateUp = {
                        onBackPressed()
                    }
                )
            }
        }
    }
}

@Composable
private fun DemoTheme(
    demoColors: DemoColors,
    window: Window,
    children: @Composable () -> Unit
) {
    MaterialTheme(demoColors.colors) {
        val statusBarColor = with(MaterialTheme.colors) {
            if (isLight) darkenedPrimary else surface.toArgb()
        }
        onCommit(statusBarColor) {
            window.statusBarColor = statusBarColor
        }
        children()
    }
}

private val Colors.darkenedPrimary: Int
    get() = with(primary) {
        copy(
            red = red * 0.75f,
            green = green * 0.75f,
            blue = blue * 0.75f
        )
    }.toArgb()

private class Navigator private constructor(
    private val backDispatcher: OnBackPressedDispatcher,
    private val launchActivityDemo: (ActivityDemo<*>) -> Unit,
    private val rootDemo: Demo,
    initialDemo: Demo,
    private val backStack: MutableList<Demo>
) {
    constructor(
        rootDemo: Demo,
        backDispatcher: OnBackPressedDispatcher,
        launchActivityDemo: (ActivityDemo<*>) -> Unit
    ) : this(backDispatcher, launchActivityDemo, rootDemo, rootDemo, mutableListOf<Demo>())

    private val onBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            popBackStack()
        }
    }.apply {
        isEnabled = !isRoot
        backDispatcher.addCallback(this)
    }

    private var _currentDemo by mutableStateOf(initialDemo)
    var currentDemo: Demo
        get() = _currentDemo
        private set(value) {
            _currentDemo = value
            onBackPressed.isEnabled = !isRoot
        }

    val isRoot: Boolean get() = backStack.isEmpty()

    val backStackTitle: String
        get() =
            (backStack.drop(1) + currentDemo).joinToString(separator = " > ") { it.title }

    fun navigateTo(demo: Demo) {
        if (demo is ActivityDemo<*>) {
            launchActivityDemo(demo)
        } else {
            backStack.add(currentDemo)
            currentDemo = demo
        }
    }

    fun popAll() {
        if (!isRoot) {
            backStack.clear()
            currentDemo = rootDemo
        }
    }

    private fun popBackStack() {
        currentDemo = backStack.removeAt(backStack.lastIndex)
    }

    companion object {
        fun Saver(
            rootDemo: DemoCategory,
            backDispatcher: OnBackPressedDispatcher,
            launchActivityDemo: (ActivityDemo<*>) -> Unit
        ): Saver<Navigator, *> = listSaver<Navigator, String>(
            save = { navigator ->
                (navigator.backStack + navigator.currentDemo).map { it.title }
            },
            restore = { restored ->
                require(restored.isNotEmpty())
                val backStack = restored.mapTo(mutableListOf()) {
                    requireNotNull(findDemo(rootDemo, it))
                }
                val initial = backStack.removeAt(backStack.lastIndex)
                Navigator(backDispatcher, launchActivityDemo, rootDemo, initial, backStack)
            }
        )

        private fun findDemo(demo: Demo, title: String): Demo? {
            if (demo.title == title) {
                return demo
            }
            if (demo is DemoCategory) {
                demo.demos.forEach { child ->
                    findDemo(child, title)
                        ?.let { return it }
                }
            }
            return null
        }
    }
}

private class FilterMode(backDispatcher: OnBackPressedDispatcher, initialValue: Boolean = false) {

    private var _isFiltering by mutableStateOf(initialValue)

    private val onBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            isFiltering = false
        }
    }.apply {
        isEnabled = initialValue
        backDispatcher.addCallback(this)
    }

    var isFiltering
        get() = _isFiltering
        set(value) {
            _isFiltering = value
            onBackPressed.isEnabled = value
        }

    companion object {
        fun Saver(backDispatcher: OnBackPressedDispatcher) = Saver<FilterMode, Boolean>(
            save = { it.isFiltering },
            restore = { FilterMode(backDispatcher, it) }
        )
    }
}
