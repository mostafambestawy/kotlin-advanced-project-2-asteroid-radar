package com.udacity.asteroidradar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.AsteroidBrief
import com.udacity.asteroidradar.AsteroidDetails
import com.udacity.asteroidradar.db.Entities.AsteroidEntity

class DAO {
    @Dao
    interface AsteroidDao {
        @Query("SELECT * FROM asteroid")
        fun getAsteroid(): LiveData<List<AsteroidEntity>>


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAsteroids(asteroidEntity: List<AsteroidEntity>)

        @Query("SELECT id,name,date,hazard_status FROM asteroid")
        fun getAsteroidsBriefs(): LiveData<List<AsteroidBrief>>

        @Query("SELECT id,hazard_status,date,absolute_magnitude,estimated_diameter,relative_velocity,distance_from_earth FROM asteroid WHERE id=:asteroid_id")
        fun getAsteroidDetails(asteroid_id:String): LiveData<AsteroidDetails>
        
    }
    @Dao
    interface PictureOfDayDao {
        @Query("SELECT * FROM picture_of_day where id = 1")
        fun getPictureOfDayEntity(): LiveData<Entities.PictureOfDayEntity>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertPictureOfDayEntity(pictureOfDayEntity: Entities.PictureOfDayEntity)

    }
}