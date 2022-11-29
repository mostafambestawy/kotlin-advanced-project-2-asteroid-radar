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

    private val _idToNavigate = MutableLiveData<String?>()
    val idToNavigate: LiveData<String?>
        get() = _idToNavigate

    fun navigateToDetailsScreen(id:String) {
        _idToNavigate.value = id
        _eventNavigateToDetailsScreen.value = true
    }

    fun onNavigateToDetailsScreen() {
        _idToNavigate.value = null
        _eventNavigateToDetailsScreen.value = false
    }

    /**
     * create repository
     */
    private val asteroidRepository:AsteroidRepository = AsteroidRepository(getRoomDB(getApplication()),viewModelScope)

    /**
     * refresh data from repository
     * */

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
            asteroidRepository.getPictureOfDay()
        }
    }
    /**
     * get live data asteroids from repository
     */

    val asteroidsBriefs = asteroidRepository.asteroidsBriefs

    val pictureOfDayEntity = asteroidRepository.pictureOfDayEntity
    // The external immutable LiveData for the request status
    val status: LiveData<RequestStatus> = asteroidRepository.status









}