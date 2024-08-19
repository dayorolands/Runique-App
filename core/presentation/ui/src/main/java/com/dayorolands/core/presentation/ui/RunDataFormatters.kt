package com.dayorolands.core.presentation.ui

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration

fun Duration.formatted(): String {
    val totalSeconds = inWholeSeconds
    val hours = String.format("%02d", totalSeconds / (60 * 60))
    val minutes = String.format("%02d", (totalSeconds % (60 * 60)) / 60)
    val seconds = String.format("%02d", totalSeconds % 60)

    return "$hours:$minutes:$seconds"
}

fun Double.toFormattedKm(): String {
    return "${this.roundToDecimals(1)} km"
}

fun Double.roundToDecimals(decimalCount: Int): Double{
    val factor = 10f.pow(decimalCount)
    return round(this * factor) / factor
}

fun Duration.toFormattedPace(distanceKm: Double): String {
    if(this == Duration.ZERO || distanceKm <= 0.0){
        return "-"
    }

    val secondsPerKm = (this.inWholeSeconds / distanceKm).roundToInt()
    val averagePaceInMinutes = secondsPerKm / 60
    val averagePaceInSeconds = String.format("%02d", secondsPerKm % 60)

    return "$averagePaceInMinutes:$averagePaceInSeconds / km"
}