package com.dayorolands.run.domain

import com.dayorolands.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow


interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}