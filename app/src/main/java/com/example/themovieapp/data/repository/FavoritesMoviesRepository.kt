package com.example.themovieapp.data.repository

import android.content.Context
import com.example.themovieapp.data.model.Movie

class FavoritesMoviesRepository(context: Context) {

    private val mDatabase = AppDatabase.getDatabase(context).favoritesMoviesDao()

    suspend fun saveFavoriteMovie(movie: Movie) = mDatabase.saveFavoriteMovie(movie)

    fun getFavoriteMovie() = mDatabase.getFavoriteMovie()

    suspend fun deleteFavoriteMovie(movie: Movie) = mDatabase.deleteFavoriteMovie(movie)

}