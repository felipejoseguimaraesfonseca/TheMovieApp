package com.example.themovieapp.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovieapp.data.model.Serie

interface FavoritesSeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteSerie(serie: Serie): Long

    @Query("SELECT * FROM series")
    fun getFavoriteSerie(): LiveData<List<Serie>>

    @Delete
    suspend fun deleteFavoriteSerie(serie: Serie)

}