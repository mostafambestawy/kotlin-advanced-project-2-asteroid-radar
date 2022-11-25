package com.udacity.asteroidradar.db

import androidx.room.*

class Entities {
    @Entity
    data class AsteroidEntity constructor(
        @PrimaryKey
        val id: String,
        val name: String,
        val date: String,
        val hazard_status: Boolean,
        val close_approach_date: String,
        val absolute_magnitude: Double,
        val estimated_diameter: Double,
        val relative_velocity: String,
        )
}