package com.example.taboogame.models

data class NewGameSettings(
    var roundTime: Long,
    var skipAvailable: Int,
    var pointsLimit: Int,
    var teamOneName: String,
    var teamTwoName: String
) {
}