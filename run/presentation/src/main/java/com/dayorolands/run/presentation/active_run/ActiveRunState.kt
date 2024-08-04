package com.dayorolands.run.presentation.active_run

import com.dayorolands.core.domain.location.Location
import kotlin.time.Duration

data class ActiveRunState(
    val elapsedTime: Duration = Duration.ZERO,
    val shouldTrack: Boolean = false,
    val hasStatedRunning: Boolean = false,
    val currentLocation: Location? = null,
    val isRunFinished: Boolean = false,
    val isSavingRun: Boolean = false
)
