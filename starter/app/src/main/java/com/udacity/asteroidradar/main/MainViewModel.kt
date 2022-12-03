package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.AsteroidBrief
import com.udacity.asteroidradar.db.getRoomDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.AsteroidsFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class RequestStatus { LOADING, ERROR, DONE }
class MainViewModel(application: Application) : AndroidViewModel(application) {
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

    fun navigateToDetailsScreen(id: String) {
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
    private val asteroidRepository: AsteroidRepository =
        AsteroidRepository(getRoomDB(getApplication()))

    /**
     * get live data asteroids from repository
     */

    private val _asteroidsBriefs = MutableLiveData<List<AsteroidBrief>>()

    val asteroidsBriefs:LiveData<List<AsteroidBrief>>
        get() = _asteroidsBriefs

    init {
        /**
         * only get asteroids if database is empty on first use
         * */
        viewModelScope.launch {
            if (emptyDatabase()) {
                asteroidRepository.refreshApiAsteroids()
                asteroidRepository.getApiPictureOfDay()
            }
        }
        /** Week data is the default view at fragment launching */
        getAsteroidsBriefs(AsteroidsFilter.SHOW_WEEK)
    }

    private suspend fun emptyDatabase(): Boolean {

        return getRoomDB(getApplication()).asteroidDao.getAsteroidsCount() < 1
    }




    val pictureOfDayEntity = asteroidRepository.pictureOfDayEntity

    // The external immutable LiveData for the request status
    val status: LiveData<RequestStatus> = asteroidRepository.status

    fun getAsteroidsBriefs(filter: AsteroidsFilter){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _asteroidsBriefs.postValue(asteroidRepository.getAsteroidsBriefs(filter))
            }
        }
    }


}