package com.example.themovieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.R
import com.example.themovieapp.data.model.Serie
import com.example.themovieapp.data.repository.FavoritesSeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesSeriesModel(
    application: Application,
    private val favoritesSeriesRepository: FavoritesSeriesRepository
) : AndroidViewModel(application) {

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun saveFavoriteSerie(serie: Serie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritesSeriesRepository.saveFavoriteSerie(serie)
                _messageEventData.postValue(R.string.favorite_serie_successfully_saved)
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.favorite_serie_failure_saved)
                Log.e(TAG, exception.toString())
            }
        }
    }

    fun getFavoriteSerie() = favoritesSeriesRepository.getFavoriteSerie()

    fun deleteFavoriteSerie(serie: Serie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritesSeriesRepository.deleteFavoriteSerie(serie)
                _messageEventData.postValue(R.string.favorite_serie_successfully_deleted)
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.favorite_serie_failure_deleted)
                Log.e(TAG, exception.toString())
            }
        }
    }

    companion object {
        private val TAG = FavoritesSeriesModel::class.java.simpleName
    }
}