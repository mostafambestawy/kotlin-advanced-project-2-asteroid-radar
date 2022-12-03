package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.db.getRoomDB
import com.udacity.asteroidradar.repository.AsteroidRepository

class SyncDataWorker (context:Context,params:WorkerParameters):CoroutineWorker(context, params){
    companion object {
        const val WORK_NAME = "SyncDataWorker"
    }
    override suspend fun doWork(): Result {
        val asteroidRoomDB = getRoomDB(applicationContext)
        val asteroidRepository = AsteroidRepository(asteroidRoomDB)
        return try {
            asteroidRepository.refreshApiAsteroids()
            asteroidRepository.getApiPictureOfDay()
            Result.success()
        } catch (e:Exception){
            Result.retry()
        }

    }

}