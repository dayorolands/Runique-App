package com.dayorolands.run.domain

import com.dayorolands.core.domain.location.Location
import com.dayorolands.core.domain.location.LocationTimeStamp
import com.dayorolands.core.domain.location.LocationWithAltitude
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationTimeStamp>> = emptyList()
)