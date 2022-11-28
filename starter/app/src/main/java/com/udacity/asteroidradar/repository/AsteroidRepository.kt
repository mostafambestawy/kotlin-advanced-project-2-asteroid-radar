package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.db.Entities
import com.udacity.asteroidradar.db.Entities.AsteroidEntity

import com.udacity.asteroidradar.db.Room.AsteroidRoomDB
import com.udacity.asteroidradar.main.RequestStatus
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.toAsteroidEntity
import com.udacity.asteroidradar.toPictureOfDayEntity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * to solve the problem od executing insert function in Call back
 * we passes the viewModelScope rather than using the GlobalScope which is very dangerous and may
 * cause memory leaks if not used carefully
 * if retrofit coroutines used no need to pass the viewModelScope and insert().await will used
 * */
class AsteroidRepository(
    private val asteroidRoomDB: AsteroidRoomDB,
    private val viewModelScope: CoroutineScope
) {
    /**
     * status live data
     * */
    val status = MutableLiveData<RequestStatus>()

    /**
     * get Asteroids from network then save it in Room Database
     * */
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            status.postValue(RequestStatus.LOADING)
            AsteroidApi.asteroidServiceInterface.getAsteroidList().enqueue(object :
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val jsonData = JSONObject(response.body().toString())
                    val asteroids = parseAsteroidsJsonResult(jsonData)
                    val asteroidsEntities = asteroids.toAsteroidEntity()
                    /**
                     * solution to execute insert function in Call back
                     * if retrofit coroutines used it shall be insert().await
                     * */

                    viewModelScope.launch(Dispatchers.IO) {
                        asteroidRoomDB.asteroidDao.insertAsteroids(asteroidsEntities)
                    }


                    status.postValue(RequestStatus.DONE)
                }


                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(RequestStatus.ERROR)
                }
            })
        }
    }

    /**
     * liveData for asteroids that exposed and can be listened from outside to get asteroids
     */

    val asteroidsEntities: LiveData<List<AsteroidEntity>> = asteroidRoomDB.asteroidDao.getAsteroid()


    /**
     * get PictureOfDay from network then save Entity  it in Room Database
     * */
    suspend fun getPictureOfDay() {
        withContext(Dispatchers.IO) {
            status.postValue(RequestStatus.LOADING)
            val pictureOfDay = AsteroidApi.asteroidServiceInterface.getPictureOfTheDay().await()
            if (pictureOfDay.mediaType.equals("image"))
                asteroidRoomDB.pictureOfDayDao.insertPictureOfDayEntity(pictureOfDay.toPictureOfDayEntity())

        }
    }


    /**
     * liveData for PictureOfDay that exposed and can be listened from outside to get PictureOfDay
     */
    val pictureOfDayEntity: LiveData<Entities.PictureOfDayEntity> =
        asteroidRoomDB.pictureOfDayDao.getPictureOfDayEntity()

}