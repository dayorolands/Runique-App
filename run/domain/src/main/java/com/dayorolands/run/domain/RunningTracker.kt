package com.dayorolands.run.domain

import com.dayorolands.core.domain.Timer
import com.dayorolands.core.domain.location.LocationTimeStamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class RunningTracker(
    private val locationObserver: LocationObserver,
    private val applicationScope: CoroutineScope
) {
    private val _runData = MutableStateFlow(RunData())
    val runData = _runData.asStateFlow()

    private val _isTrackingActiveRun = MutableStateFlow(false)
    val isTrackingActiveRun = _isTrackingActiveRun.asStateFlow()
    private val isObservingLocation = MutableStateFlow(false)

    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow()

    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->
            if(isObservingLocation) {
                locationObserver.observeLocation(1000L)
            } else flowOf()
        }
        .stateIn(
            applicationScope,
            SharingStarted.Lazily,
            null
        )

    init {
        _isTrackingActiveRun
            .onEach { isTracking ->
                if(isTracking.not()) {
                    val newLocationList = buildList {
                        addAll(runData.value.locations)
                        add(emptyList<LocationTimeStamp>())
                    }.toList()
                    _runData.update { it.copy(
                        locations = newLocationList
                    ) }
                }
            }
            .flatMapLatest { isTracking ->
                if(isTracking) {
                    Timer.timeAndEmit()
                } else flowOf()
            }
            .onEach {
                _elapsedTime.value += it
            }
            .launchIn(applicationScope)

        currentLocation
            .filterNotNull()
            .combineTransform(_isTrackingActiveRun) { location, isTracking ->
                if(isTracking) {
                    emit(location)
                }
            }
            .zip(_elapsedTime) { location, elapsedTime ->
                LocationTimeStamp(
                    location = location,
                    durationTimestamp = elapsedTime
                )
            }
            .onEach { location ->
                val currentLocations = runData.value.locations
                val lastLocationsList = if(currentLocations.isNotEmpty()) {
                    currentLocations.last() + location
                } else listOf(location)
                val newLocationsList = currentLocations.replaceLast(lastLocationsList)
                val distanceMeters = LocationDataCalculator.getTotalDistanceMeters(
                    locations = newLocationsList
                )
                val distanceKm = distanceMeters / 1000.0
                val currentDuration = location.durationTimestamp
                val avgSecondsPerKm = if(distanceKm == 0.0) {
                    0
                } else {
                    (currentDuration.inWholeSeconds / distanceKm).roundToInt()
                }
                _runData.update {
                    RunData(
                        distanceMeters = distanceMeters,
                        pace = avgSecondsPerKm.seconds,
                        locations = newLocationsList
                    )
                }
            }
            .launchIn(applicationScope)
    }

    fun setIsTrackingActiveRun(isTracking: Boolean) {
        this._isTrackingActiveRun.value = isTracking
    }

    fun startObservingLocation(){
        isObservingLocation.value = true
    }

    fun stopObservingLocation(){
        isObservingLocation.value = false
    }

    fun finishRun() {
        stopObservingLocation()
        setIsTrackingActiveRun(false)
        _elapsedTime.value = Duration.ZERO
        _runData.value = RunData()
    }
}

private fun <T> List<List<T>>.replaceLast(replacementList: List<T>) : List<List<T>> {
    if(this.isEmpty()) {
        return listOf(replacementList)
    }
    return this.dropLast(1) + listOf(replacementList)
}