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
import java.text.SimpleDateFormat
import java.util.*

class DAO {
    @Dao
    interface AsteroidDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAsteroids(asteroidEntity: List<AsteroidEntity>)

        @Query("SELECT id,name,date,hazard_status FROM asteroid WHERE date >= :today ORDER BY date")
        fun getAsteroidsBriefs(today: String = this.getToday()): LiveData<List<AsteroidBrief>>



        @Query("SELECT id,hazard_status,date,absolute_magnitude,estimated_diameter,relative_velocity," +
                "distance_from_earth FROM asteroid WHERE id=:asteroid_id")
        fun getAsteroidDetails(asteroid_id:String): LiveData<AsteroidDetails>



        private fun getToday():String{
            val calendar = Calendar.getInstance()
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            return dateFormat.format(currentTime)
        }
        
    }
    @Dao
    interface PictureOfDayDao {
        @Query("SELECT * FROM picture_of_day where id = 1")
        fun getPictureOfDayEntity(): LiveData<Entities.PictureOfDayEntity>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertPictureOfDayEntity(pictureOfDayEntity: Entities.PictureOfDayEntity)

    }



}