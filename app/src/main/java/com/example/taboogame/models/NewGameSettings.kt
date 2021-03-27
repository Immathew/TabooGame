package com.example.taboogame.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewGameSettings(
    var roundTime: Long,
    var skipAvailable: Int,
    var pointsLimit: Int,
    var teamOneName: String,
    var teamTwoName: String,
    var vibration: Boolean,
    var language: String
) : Parcelable