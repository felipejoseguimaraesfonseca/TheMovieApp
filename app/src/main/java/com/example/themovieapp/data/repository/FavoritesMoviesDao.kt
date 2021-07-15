package com.example.themovieapp.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovieapp.data.model.Movie

interface FavoritesMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteMovie(movie: Movie): Long

    @Query("SELECT * FROM movies")
    fun getFavoriteMovie(): LiveData<List<Movie>>

    @Delete
    suspend fun deleteFavoriteMovie(movie: Movie)

}