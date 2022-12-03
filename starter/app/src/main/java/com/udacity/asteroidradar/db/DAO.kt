package com.udacity.asteroidradar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.AsteroidBrief
import com.udacity.asteroidradar.AsteroidDetails
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.db.Entities.AsteroidEntity
import com.udacity.asteroidradar.network.getToday
import com.udacity.asteroidradar.network.getWeekDay
import java.text.SimpleDateFormat
import java.util.*

class DAO {
    @Dao
    interface AsteroidDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAsteroids(asteroidEntity: List<AsteroidEntity>)

        @Query("SELECT id,name,date,hazard_status FROM asteroid WHERE date >= :today ORDER BY date")
        fun getAsteroidsBriefs(today: String = getToday()): LiveData<List<AsteroidBrief>>

        @Query("SELECT id,name,date,hazard_status FROM asteroid WHERE date = :today ORDER BY date")
        fun getTodayAsteroidsBriefs(today: String = getToday()): List<AsteroidBrief>

        @Query("SELECT id,name,date,hazard_status FROM asteroid WHERE date <=:startDay ORDER BY date")
        fun getWeekAsteroidsBriefs(startDay: String = getWeekDay()): List<AsteroidBrief>



        @Query("SELECT id,hazard_status,date,absolute_magnitude,estimated_diameter,relative_velocity," +
                "distance_from_earth FROM asteroid WHERE id=:asteroid_id")
        fun getAsteroidDetails(asteroid_id:String): LiveData<AsteroidDetails>

        @Query("SELECT COUNT(id) FROM asteroid")
        suspend fun getAsteroidsCount(): Long




        
    }
    @Dao
    interface PictureOfDayDao {
        @Query("SELECT * FROM picture_of_day where id = 1")
        fun getPictureOfDayEntity(): LiveData<Entities.PictureOfDayEntity>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertPictureOfDayEntity(pictureOfDayEntity: Entities.PictureOfDayEntity)

    }



}