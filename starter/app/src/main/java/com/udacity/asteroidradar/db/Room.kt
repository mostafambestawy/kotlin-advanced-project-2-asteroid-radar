package com.udacity.asteroidradar.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.db.DAO.AsteroidDao
import com.udacity.asteroidradar.db.Entities.AsteroidEntity
import com.udacity.asteroidradar.db.Room.AsteroidRoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Room {
    @Database(
        entities = [AsteroidEntity::class, Entities.PictureOfDayEntity::class],
        version = 2,
        autoMigrations = [AutoMigration(from = 1, to = 2)]
    )
    abstract class AsteroidRoomDB : RoomDatabase() {
        abstract val asteroidDao: AsteroidDao
        abstract val pictureOfDayDao: DAO.PictureOfDayDao
    }
}

val TABLES = listOf(AsteroidEntity.TABLE_NAME ,Entities.PictureOfDayEntity.TABLE_NAME)
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        for (table in TABLES) {
            database.execSQL("DROP TABLE IF EXIST $table")
        }


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
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // moving to a new thread
                        synchronized(context) {

                            /** populate data using Dao's or execSQL */
                            val queries = context.resources.getStringArray(R.array.data_populating_queries)
                            for(query in queries){
                            db.execSQL(query)
                            }

                        }

                    }
                })
                .build()
        }
    }
    return INSTANCE

}
fun buildRoomDB(){

}