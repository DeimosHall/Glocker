package com.deimos.glocker

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyApp() {
    val myOptions = getOptions(titles = listOf("Capital letters", "Numbers", "Symbols"))
    val mySlider = getSliderInfo()

    Column {
        MyTitle()
        Box(modifier = Modifier.padding(25.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                //MyCard(myOptions, mySlider)
                TopContent(options = myOptions, slider = mySlider)
                GenerateButton()
            }
        }
    }
}

@Composable
fun TopContent(options: List<CheckboxInfo>, slider: SliderInfo) {
    Column {
        LockIcon()
        MyCard(options = options, slider = slider)
    }
}

@Composable
fun MyTitle() {
    Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), contentAlignment = Alignment.Center) {
        Text(text = "Glocker", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LockIcon() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, bottom = 40.dp),
        contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(id = R.drawable.ic_lock_100),
            contentDescription = "Lock icon",
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
fun MyCard(options: List<CheckboxInfo>, slider: SliderInfo) {
    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 10.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Column {
                    options.forEach { MyCheckbox(checkboxInfo = it) }
                }
                PasswordLength(slider = slider)
                MySlider(slider)
                PasswordField()
            }
        }
    }
}

@Composable
fun GenerateButton() {
    Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Generate")
    }
}

@Composable
fun PasswordField() {
    Box(modifier = Modifier.padding(15.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Password") },
            shape = RoundedCornerShape(16.dp),
            enabled = false,
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_content_copy_24),
                        contentDescription = "Copy icon"
                    )
                }
            }
        )
    }
}

@Composable
fun PasswordLength(slider: SliderInfo) {
    Box(modifier = Modifier.padding(start = 15.dp, bottom = 10.dp, top = 15.dp)) {
        Text(text = "Length: ${slider.position.toInt()}")
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
    Slider(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        value = sliderInfo.position,
        onValueChange = { sliderInfo.onSliderChange(it) },
        valueRange = 5f..20f,
        steps = 14
    )
}