package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.db.Entities.AsteroidEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: String, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: String, val distanceFromEarth: String,
    val isPotentiallyHazardous: Boolean
) : Parcelable

fun List<Asteroid>.toAsteroidEntity(): List<AsteroidEntity> {
    return map {
        AsteroidEntity(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.isPotentiallyHazardous,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth
        )
    }
}