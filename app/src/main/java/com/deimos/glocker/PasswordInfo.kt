package com.deimos.glocker

data class PasswordInfo(
    val value: String,
    var onValueChange: (String) -> Unit
)