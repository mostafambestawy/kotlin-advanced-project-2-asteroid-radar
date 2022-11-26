package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.db.Entities
import com.udacity.asteroidradar.db.Room.AsteroidRoomDB
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.toAsteroidEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.udacity.asteroidradar.db.Entities.AsteroidEntity
import com.udacity.asteroidradar.main.RequestStatus

class AsteroidRepository(private val asteroidRoomDB: AsteroidRoomDB) {
    /**
     * status live data
     * */
    val status = MutableLiveData<RequestStatus>()
    /**
     * get Asteroids from network then save it in Room Database
     * */
    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            status.value = RequestStatus.LOADING
            AsteroidApi.asteroidServiceInterface.getAsteroidList().enqueue(object:
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val jsonData = JSONObject(response.body().toString())
                    val asteroids = parseAsteroidsJsonResult(jsonData)
                    val asteroidsEntities = asteroids.toAsteroidEntity()
                    asteroidRoomDB.asteroidDao.insertAsteroids(asteroidsEntities)
                    status.value = RequestStatus.DONE
                }



                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.value = RequestStatus.ERROR
                }
            })
        }
    }

    /**
     * liveData for asteroids that exposed and can be listened from outside to get asteroids
     */

    val asteroidsEntities : LiveData<List<AsteroidEntity>> = asteroidRoomDB.asteroidDao.getAsteroid()

}