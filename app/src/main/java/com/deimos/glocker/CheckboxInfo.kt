package com.deimos.glocker

data class CheckboxInfo(
    val title: String,
    val selected: Boolean,
    var onCheckedChange: (Boolean) -> Unit
)