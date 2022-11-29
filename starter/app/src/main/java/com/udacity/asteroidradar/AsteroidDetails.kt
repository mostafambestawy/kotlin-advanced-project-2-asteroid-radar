package com.udacity.asteroidradar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AsteroidDetails(
    val id: String,val hazard_status: Boolean,val date: String,val absolute_magnitude: Double,
    val estimated_diameter: Double, val relative_velocity: String,
    val distance_from_earth: String
) : Parcelable
