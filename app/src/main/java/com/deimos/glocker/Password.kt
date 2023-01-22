package com.deimos.glocker

class Password {
    fun generate(hasNumbers: Boolean, hasCapLetters: Boolean, hasSymbols: Boolean, length: Int): String {
        val symbolsList = listOf('!', '@', '#', '$', '%', '^', '&', '*')
        val numbersList = ('0'..'9').toList()
        val capLettersList = ('A'..'Z').toList()
        var possibleChars = ('a'..'z').toList()

        if (hasNumbers) possibleChars += numbersList
        if (hasCapLetters) possibleChars += capLettersList
        if (hasSymbols) possibleChars += symbolsList

        return (1..length).map { possibleChars.random() }.joinToString("")
    }
}