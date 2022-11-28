package com.udacity.asteroidradar.db

import androidx.room.*
import com.udacity.asteroidradar.PictureOfDay

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
        ){
        companion object {
            const val TABLE_NAME = "asteroid"
        }
    }
    @Entity(tableName = "picture_of_day")
    data class PictureOfDayEntity constructor(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "media_type")
        val mediaType: String,
        val title: String,
        val url: String,
    ){
        companion object {
            const val TABLE_NAME = "asteroid"
        }

    }

}
fun Entities.PictureOfDayEntity.toPictureOfDay():PictureOfDay{
    return PictureOfDay(mediaType,title,url)
}
