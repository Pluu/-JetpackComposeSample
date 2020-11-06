package com.pluu.composesample.sample.modifier

import androidx.compose.animation.animate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class DrawModel {
    var scaleX by mutableStateOf(0.75f)
    var scaleY by mutableStateOf(0.75f)
    var rotationX by mutableStateOf(0f)
    var rotationY by mutableStateOf(0f)
    var rotationZ by mutableStateOf(0f)
    var elevation by mutableStateOf(0f)
    var alpha by mutableStateOf(1f)
}

@Composable
fun DrawLayerApp() {
    val model = remember { DrawModel() }
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Config(model)
        Spacer(modifier = Modifier.weight(1f))
        Display(model)
    }
}

@Composable
private fun Config(model: DrawModel) {
    LabeledSlider(
        label = "ScaleX",
        value = model.scaleX,
        range = 0f..2f,
        onChanged = model::scaleX::set
    )
    LabeledSlider(
        label = "ScaleY",
        value = model.scaleY,
        range = 0f..2f,
        onChanged = model::scaleY::set
    )
    LabeledSlider(
        label = "RotationX",
        value = model.rotationX,
        range = -180f..180f,
        onChanged = model::rotationX::set
    )
    LabeledSlider(
        label = "RotationY",
        value = model.rotationY,
        range = -180f..180f,
        onChanged = model::rotationY::set
    )
    LabeledSlider(
        label = "RotationZ",
        value = model.rotationZ,
        range = -180f..180f,
        onChanged = model::rotationZ::set
    )
    LabeledSlider(
        label = "Elevation",
        value = model.elevation,
        range = 0f..10f,
        onChanged = model::elevation::set
    )
    LabeledSlider(
        label = "Alpha",
        value = model.alpha,
        range = 0f..1f,
        onChanged = {
            model.alpha = it
        }
    )
}

@Composable
private fun LabeledSlider(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onChanged: (Float) -> Unit
) {
    Text(label, color = MaterialTheme.colors.onSurface)
    Slider(value = value, valueRange = range, onValueChange = onChanged)
}

@Composable
private fun Display(model: DrawModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .padding(4.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        Surface(
            modifier = Modifier.aspectRatio(1f)
                .drawLayer(
                    scaleX = animate(model.scaleX),
                    scaleY = animate(model.scaleY),
                    rotationX = animate(model.rotationX),
                    rotationY = animate(model.rotationY),
                    rotationZ = animate(model.rotationZ),
                    shadowElevation = animate(model.elevation),
                    // Animating the alpha just look janky.
                    alpha = model.alpha
                ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.Red),
            color = Color.LightGray
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Sample") },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.ArrowBack)
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {}) {
                        Icon(Icons.Default.Add)
                    }
                }
            ) {
                Column(modifier = Modifier.padding(4.dp)) {
                    Text("Demo drawLayer")
                    CircularProgressIndicator()
                }
            }
        }
    }
}
