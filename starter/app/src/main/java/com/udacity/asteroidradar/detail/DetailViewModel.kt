package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.AsteroidDetails
import com.udacity.asteroidradar.db.getRoomDB
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


    /**
     * create repository
     */
    private val asteroidRepository: AsteroidRepository =
        AsteroidRepository(getRoomDB(getApplication()), viewModelScope)


    lateinit var asteroidDetail: LiveData<AsteroidDetails>


    fun getAsteroidDetail(id:String?){
        viewModelScope.launch {
            asteroidDetail = asteroidRepository.getAsteroidDetails(id!!)
        }
    }

    fun displayAstronomicalUnitExplanationDialog(){

    }




}