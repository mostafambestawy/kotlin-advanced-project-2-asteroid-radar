package com.udacity.asteroidradar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.udacity.asteroidradar.db.Entities.AsteroidEntity

class DAO {
    @Dao
    interface AsteroidDao{
        @Query("SELECT * FROM asteroid")
        fun getAsteroid(): LiveData<List<AsteroidEntity>>
    }
}