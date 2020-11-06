package com.pluu.composesample

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LayoutDirectionAmbient
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.pluu.composesample.demo.*

@Composable
fun DemoApp(
    currentDemo: Demo,
    backStackTitle: String,
    isFiltering: Boolean,
    onStartFiltering: () -> Unit,
    onEndFiltering: () -> Unit,
    onNavigateToDemo: (Demo) -> Unit,
    canNavigateUp: Boolean,
    onNavigateUp: () -> Unit
) {
    val navigationIcon = (@Composable { AppBarIcons.Back(onNavigateUp) }).takeIf { canNavigateUp }

    var filterText by savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }

    Scaffold(
        topBar = {
            DemoAppBar(
                title = backStackTitle,
                navigationIcon = navigationIcon,
                isFiltering = isFiltering,
                filterText = filterText,
                onFilter = { filterText = it },
                onStartFiltering = onStartFiltering,
                onEndFiltering = onEndFiltering
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        DemoContent(modifier, currentDemo, isFiltering, filterText.text, onNavigateToDemo)
    }
}

@Composable
private fun DemoContent(
    modifier: Modifier,
    currentDemo: Demo,
    isFiltering: Boolean,
    filterText: String,
    onNavigate: (Demo) -> Unit
) {
    Crossfade(isFiltering to currentDemo) { (filtering, demo) ->
        Surface(modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            if (filtering) {
                DemoFilter(
                    launchableDemos = AllDemosCategory.allLaunchableDemos(),
                    filterText = filterText,
                    onNavigate = onNavigate
                )
            } else {
                DisplayDemo(demo, onNavigate)
            }
        }
    }
}

@Composable
private fun DisplayDemo(demo: Demo, onNavigate: (Demo) -> Unit) {
    when (demo) {
        is ActivityDemo<*> -> {
            /* should never get here as activity demos are not added to the backstack*/
        }
        is ComposableDemo -> demo.content()
        is DemoCategory -> DisplayDemoCategory(demo, onNavigate)
    }
}

@Composable
private fun DisplayDemoCategory(category: DemoCategory, onNavigate: (Demo) -> Unit) {
    ScrollableColumn {
        category.demos.forEach { demo ->
            ListItem(
                text = {
                    Text(
                        modifier = Modifier.preferredHeight(56.dp)
                            .wrapContentSize(Alignment.Center),
                        text = demo.title
                    )
                },
                modifier = Modifier.clickable { onNavigate(demo) }
            )
        }
    }
}

@Composable
private fun DemoAppBar(
    title: String,
    navigationIcon: @Composable (() -> Unit)?,
    isFiltering: Boolean,
    filterText: TextFieldValue,
    onFilter: (TextFieldValue) -> Unit,
    onStartFiltering: () -> Unit,
    onEndFiltering: () -> Unit
) {
    if (isFiltering) {
        FilterAppBar(
            filterText = filterText,
            onFilter = onFilter,
            onClose = onEndFiltering
        )
    } else {
        TopAppBar(
            title = {
                Text(title, Modifier.testTag(Tags.AppBarTitle))
            },
            navigationIcon = navigationIcon
//            actions = {
//                AppBarIcons.Filter(onClick = onStartFiltering)
//            }
        )
    }
}

private object AppBarIcons {
    @Composable
    fun Back(onClick: () -> Unit) {
        val icon = when (LayoutDirectionAmbient.current) {
            LayoutDirection.Ltr -> Icons.Filled.ArrowBack
            LayoutDirection.Rtl -> Icons.Filled.ArrowForward
        }
        IconButton(onClick = onClick) {
            Icon(icon)
        }
    }

    @Composable
    fun Filter(onClick: () -> Unit) {
        IconButton(modifier = Modifier.testTag(Tags.FilterButton), onClick = onClick) {
            Icon(Icons.Filled.Search)
        }
    }
}
