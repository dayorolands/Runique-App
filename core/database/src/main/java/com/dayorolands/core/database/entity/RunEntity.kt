package com.dayorolands.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity
data class RunEntity(
    val durationMillis: Long,
    val distanceMeters: Int,
    val dateTimeUtc: String,
    val latitude: Double,
    val longitude: Double,
    val avgSpeedKm: Double,
    val maxSpeedKm: Double,
    val totalElevationMeters: Int,
    val mapPictureUrl: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString()
)
