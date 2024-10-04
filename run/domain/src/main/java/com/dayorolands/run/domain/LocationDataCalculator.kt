package com.dayorolands.run.domain

import com.dayorolands.core.domain.location.LocationTimeStamp
import kotlin.math.roundToInt

object LocationDataCalculator {

    fun getTotalDistanceMeters(locations: List<List<LocationTimeStamp>>) : Int {
        return locations
            .sumOf { timeStampsPerLine ->
                timeStampsPerLine.zipWithNext { location1, location2 ->
                    location1.location.location.distanceTo(location2.location.location)
                }.sum().roundToInt()
            }
    }
}