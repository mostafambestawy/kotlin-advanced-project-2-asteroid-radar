package com.udacity.asteroidradar.db

import androidx.room.*

class Entities {
    @Entity(tableName = "asteroid")
    data class AsteroidEntity constructor(
        @PrimaryKey
        val id: String,
        val name: String,
        val date: String,
        val hazard_status: Boolean,
        val absolute_magnitude: Double,
        val estimated_diameter: Double,
        val relative_velocity: String,
        val distance_from_earth: String
        )
}