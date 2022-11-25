package com.udacity.asteroidradar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.db.Entities.AsteroidEntity
import com.udacity.asteroidradar.db.Room.AsteroidRoomDB

class Room {
    @Database(entities = [AsteroidEntity::class], version = 1)
    abstract class AsteroidRoomDB : RoomDatabase() {
        abstract val asteroidDao: DAO.AsteroidDao
    }
}

private lateinit var INSTANCE: AsteroidRoomDB
fun getRoomDB(context: Context): AsteroidRoomDB {
    synchronized(AsteroidRoomDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidRoomDB::class.java,
                "asteroid_db"
            ).build()
        }
    }
    return INSTANCE

}