package com.dayorolands.run.location

import android.location.Location
import com.dayorolands.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude() : LocationWithAltitude {
    return LocationWithAltitude(
        location = com.dayorolands.core.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}