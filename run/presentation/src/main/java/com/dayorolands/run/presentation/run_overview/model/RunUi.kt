package com.dayorolands.run.presentation.run_overview.model

data class RunUi(
    val id: String,
    val duration: String,
    val dateTimeUtc: String,
    val distance: String,
    val avgSpeed: String,
    val maxSpeed: String,
    val pace: String,
    val totalElevation: String,
    val mapPictureUrl: String?
)
