package com.deimos.glocker

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
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

    Box(modifier = Modifier.padding(15.dp)) {
        MyCard(myOptions)
    }
}

@Composable
fun MyCard(options: List<CheckboxInfo>) {
    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 10.dp
        ) {
            Column {
                options.forEach { MyCheckbox(checkboxInfo = it) }
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