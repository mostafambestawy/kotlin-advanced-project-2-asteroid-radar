package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.db.Room
import com.udacity.asteroidradar.db.getRoomDB
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.toAsteroidEntity
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class RequestStatus { LOADING, ERROR, DONE }
class MainViewModel(application: Application) : AndroidViewModel(application){
    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }









    private val _eventNavigateToDetailsScreen = MutableLiveData<Boolean>()
    val eventNavigateToDetailsScreen: LiveData<Boolean>
        get() = _eventNavigateToDetailsScreen

    fun navigateToDetailsScreen() {
        _eventNavigateToDetailsScreen.value = true
    }

    fun onNavigateToDetailsScreen() {
        _eventNavigateToDetailsScreen.value = false
    }

    /**
     * create repository
     */
    val asteroidRepository:AsteroidRepository = AsteroidRepository(getRoomDB(getApplication()))

    /**
     * refresh data from repository
     * */

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }
    /**
     * get live data asteroids from repository
     */
    val asteroidsEntities = asteroidRepository.asteroidsEntities
    // The external immutable LiveData for the request status
    val status: LiveData<RequestStatus> = asteroidRepository.status


        /**
         * coroutine
         * */
        /*viewModelScope.launch {
            try {
                val asteroids = AsteroidApi.asteroidServiceInterface.getAsteroidList().await()
                Log.i("jsonData", asteroids.size.toString())
            }
            catch (e:Exception){
                Log.i("jsonData",e.message.toString())
            }
        }

    }
    */





}