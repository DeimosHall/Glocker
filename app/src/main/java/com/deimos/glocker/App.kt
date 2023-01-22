package com.deimos.glocker

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun MyApp() {
    val context = LocalContext.current
    val myOptions = getOptions(titles = listOf("Capital letters", "Numbers", "Symbols"))
    var length by rememberSaveable { mutableStateOf(16f) }
    val mySlider = getSliderInfo()

    Box(modifier = Modifier.padding(25.dp)) {
        Column {
            MyCard(myOptions, mySlider)
            Button(onClick = {
                Log.d("MYTAG", "Lenght: ${mySlider.position}")
            }) {
                Text(text = "Button")
            }
        }
    }
}

@Composable
fun MyCard(options: List<CheckboxInfo>, slider: SliderInfo) {
    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 10.dp
        ) {
            Column {
                Column {
                    options.forEach { MyCheckbox(checkboxInfo = it) }
                }
                MySlider(slider)
            }
        }
    }
}

@Composable
fun MyCheckbox(checkboxInfo: CheckboxInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checkboxInfo.selected,
            onCheckedChange = { checkboxInfo.onCheckedChange(!checkboxInfo.selected) })
        ClickableText(
            text = AnnotatedString(checkboxInfo.title),
            onClick = { checkboxInfo.onCheckedChange(!checkboxInfo.selected) }
        )
    }
}

@Composable
fun getOptions(titles: List<String>): List<CheckboxInfo> {
    return titles.map { title ->
        var status by rememberSaveable { mutableStateOf(false) }
        CheckboxInfo(
            title = title,
            selected = status,
            onCheckedChange = { status = it }
        )
    }
}

@Composable
fun getSliderInfo(): SliderInfo {
    var position by rememberSaveable { mutableStateOf(8f) }
    return SliderInfo(position, onSliderChange = { position = it })
}

@Composable
fun MySlider(sliderInfo: SliderInfo) {
    var position by rememberSaveable { mutableStateOf(sliderInfo.position) }
    var completedPosition by rememberSaveable { mutableStateOf("") }
    Slider(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        value = sliderInfo.position,
        onValueChange = { sliderInfo.onSliderChange(it) },
        onValueChangeFinished = { completedPosition = sliderInfo.position.toString() },
        valueRange = 5f..20f,
        steps = 14
    )
}