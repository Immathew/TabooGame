package com.example.taboogame.data

data class GuessWord(
    val id: Int,
    val wordToGuess: String,
    val forbiddenWordOne: String,
    val forbiddenWordTwo: String,
    val forbiddenWordThree: String,
    val forbiddenWordFour: String
)