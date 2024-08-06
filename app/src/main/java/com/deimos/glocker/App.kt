package com.deimos.glocker

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deimos.glocker.ui.theme.GlockerTheme

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    GlockerTheme {
        MyApp()
    }
}

@Composable
fun MyApp() {
    val myOptions = getOptions(titles = listOf("Capital letters", "Numbers", "Symbols"))
    val mySlider = getSliderInfo()
    val myPassword = getPassword()
    val context = LocalContext.current

    Column {
        MyTitle()
        Box(modifier = Modifier.padding(25.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TopContent(options = myOptions, slider = mySlider, myPassword, context)
                GenerateButton(options = myOptions, slider = mySlider, myPassword)
            }
        }
    }
}

@Composable
fun TopContent(
    options: List<CheckboxInfo>,
    slider: SliderInfo,
    password: PasswordInfo,
    context: Context
) {
    Column {
        LockIcon()
        MyCard(options = options, slider = slider, password = password, context)
    }
}

@Composable
fun MyTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = "Glocker", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LockIcon() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_lock_100),
            contentDescription = "Lock icon",
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
fun MyCard(
    options: List<CheckboxInfo>,
    slider: SliderInfo,
    password: PasswordInfo,
    context: Context
) {
    Box {
        Card(
            modifier = Modifier.fillMaxWidth(), elevation = 10.dp, shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Column {
                    options.forEach { MyCheckbox(checkboxInfo = it) }
                }
                PasswordLength(slider = slider)
                MySlider(slider)
                PasswordField(options = options, slider = slider, password = password, context)
            }
        }
    }
}

@Composable
fun GenerateButton(options: List<CheckboxInfo>, slider: SliderInfo, password: PasswordInfo) {
    val passwordManager = PasswordManager()
    val myPassword = passwordManager.generate(
        hasCapLetters = options[0].selected,
        hasNumbers = options[1].selected,
        hasSymbols = options[2].selected,
        length = slider.position.toInt()
    )
    Button(onClick = { password.onValueChange(myPassword) }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Generate")
    }
}

@Composable
fun getPassword(): PasswordInfo {
    var password by rememberSaveable { mutableStateOf("") }
    return PasswordInfo(value = password, onValueChange = { password = it })
}

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("password", text)
    clipboardManager.setPrimaryClip(clip)
}

@Composable
fun PasswordField(
    options: List<CheckboxInfo>,
    slider: SliderInfo,
    password: PasswordInfo,
    context: Context
) {
    Box(modifier = Modifier.padding(15.dp)) {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = {},
            placeholder = { Text(text = "Password") },
            shape = RoundedCornerShape(16.dp),
            enabled = false,
            trailingIcon = {
                IconButton(onClick = {
                    copyToClipboard(
                        context = context,
                        text = password.value
                    )
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_content_copy_24),
                        contentDescription = "Copy icon"
                    )
                }
            })
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
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(checked = checkboxInfo.selected,
            onCheckedChange = { checkboxInfo.onCheckedChange(!checkboxInfo.selected) })
        ClickableText(text = AnnotatedString(checkboxInfo.title),
            onClick = { checkboxInfo.onCheckedChange(!checkboxInfo.selected) })
    }
}

@Composable
fun getOptions(titles: List<String>): List<CheckboxInfo> {
    return titles.map { title ->
        var status by rememberSaveable { mutableStateOf(false) }
        CheckboxInfo(title = title, selected = status, onCheckedChange = { status = it })
    }
}

@Composable
fun getSliderInfo(): SliderInfo {
    var position by rememberSaveable { mutableStateOf(16f) }
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