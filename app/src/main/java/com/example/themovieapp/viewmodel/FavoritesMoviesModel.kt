package com.example.themovieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.R
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.data.repository.FavoritesMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesMoviesModel(
    application: Application,
    private val favoritesMoviesRepository: FavoritesMoviesRepository
) : AndroidViewModel(application) {

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun saveFavoriteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritesMoviesRepository.saveFavoriteMovie(movie)
                _messageEventData.postValue(R.string.favorite_movie_successfully_saved)
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.favorite_movie_failure_saved)
                Log.e(TAG, exception.toString())
            }
        }
    }

    fun getFavoriteMovie() = favoritesMoviesRepository.getFavoriteMovie()

    fun deleteFavoriteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritesMoviesRepository.deleteFavoriteMovie(movie)
                _messageEventData.postValue(R.string.favorite_movie_successfully_deleted)
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.favorite_movie_failure_deleted)
                Log.e(TAG, exception.toString())
            }
        }
    }

    companion object {
        private val TAG = FavoritesMoviesModel::class.java.simpleName
    }
}