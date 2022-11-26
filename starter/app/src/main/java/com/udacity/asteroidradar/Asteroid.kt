package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.db.Entities
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: String, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: String, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable

fun List<Asteroid>.toAsteroidEntity(): List<Entities.AsteroidEntity> {
    return map {
        Entities.AsteroidEntity(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.isPotentiallyHazardous,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity
        )
    }
}