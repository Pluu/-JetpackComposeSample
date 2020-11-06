package com.pluu.composesample

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pluu.composesample.demo.Demo

/**
 * A scrollable list of [launchableDemos], filtered by [filterText].
 */
@Composable
fun DemoFilter(launchableDemos: List<Demo>, filterText: String, onNavigate: (Demo) -> Unit) {
    val filteredDemos = launchableDemos
        .filter { it.title.contains(filterText, ignoreCase = true) }
        .sortedBy { it.title }
    ScrollableColumn {
        filteredDemos.forEach { demo ->
            FilteredDemoListItem(
                demo,
                filterText = filterText,
                onNavigate = onNavigate
            )
        }
    }
}

/**
 * [TopAppBar] with a text field allowing filtering all the demos.
 */
@Composable
fun FilterAppBar(
    filterText: TextFieldValue,
    onFilter: (TextFieldValue) -> Unit,
    onClose: () -> Unit
) {
    with(MaterialTheme.colors) {
        val appBarColor = if (isLight) {
            surface
        } else {
            // Blending primary over surface according to Material design guidance for brand
            // surfaces in dark theme
            primary.copy(alpha = 0.08f).compositeOver(surface)
        }
        TopAppBar(backgroundColor = appBarColor, contentColor = onSurface) {
            IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = onClose) {
                Icon(Icons.Filled.Close)
            }
            FilterField(
                filterText,
                onFilter,
                Modifier.fillMaxWidth().align(Alignment.CenterVertically)
            )
        }
    }
}

/**
 * [BasicTextField] that edits the current [filterText], providing [onFilter] when edited.
 */
@Composable
@OptIn(
    ExperimentalFocus::class,
    ExperimentalFoundationApi::class
)
private fun FilterField(
    filterText: TextFieldValue,
    onFilter: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = FocusRequester()
    // TODO: replace with Material text field when available
    TextField(
        modifier = modifier.focusRequester(focusRequester),
        value = filterText,
        onValueChange = onFilter,
        textStyle = AmbientTextStyle.current,
        activeColor = AmbientContentColor.current
    )

    onCommit { focusRequester.requestFocus() }
}

/**
 * [ListItem] that displays a [demo] and highlights any matches for [filterText] inside [Demo.title]
 */
@Composable
private fun FilteredDemoListItem(
    demo: Demo,
    filterText: String,
    onNavigate: (Demo) -> Unit
) {
    val primary = MaterialTheme.colors.primary
    val annotatedString = annotatedString {
        val title = demo.title
        var currentIndex = 0
        val pattern = filterText.toRegex(option = RegexOption.IGNORE_CASE)
        pattern.findAll(title).forEach { result ->
            val index = result.range.first
            if (index > currentIndex) {
                append(title.substring(currentIndex, index))
                currentIndex = index
            }
            withStyle(SpanStyle(color = primary)) {
                append(result.value)
            }
            currentIndex = result.range.last + 1
        }
        if (currentIndex <= title.lastIndex) {
            append(title.substring(currentIndex, title.length))
        }
    }
    key(demo.title) {
        ListItem(
            text = {
                Text(
                    modifier = Modifier.preferredHeight(56.dp).wrapContentSize(Alignment.Center),
                    text = annotatedString
                )
            },
            modifier = Modifier.clickable { onNavigate(demo) }
        )
    }
}
