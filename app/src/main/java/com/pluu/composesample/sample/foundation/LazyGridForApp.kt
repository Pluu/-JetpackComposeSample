package com.pluu.composesample.sample.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pluu.compose.ui.foundation.lazy.LazyGridFor

val list = (0..100)
    .map { "Item $it" }
    .toList()

@Composable
fun LazyGridForApp() {
    LazyGridFor(
        items = list,
        rows = 2,
        modifier = Modifier.fillMaxSize()
    ) { item, index ->
        Card(
            elevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(4.dp)
        ) {
            Text(
                text = "[$index] $item",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillParentMaxSize().wrapContentSize(),
            )
        }
    }
}